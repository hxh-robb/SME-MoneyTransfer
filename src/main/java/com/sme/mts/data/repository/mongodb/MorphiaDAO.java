package com.sme.mts.data.repository.mongodb;

import com.mongodb.WriteResult;
import com.sme.mts.data.Data;
import com.sme.mts.data.LoggingDAO;
import com.sme.mts.data.repository.DocDAO;
import org.apache.commons.logging.LogFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MorphiaDAO<D extends Data, F extends DocDAO.Filter> extends LoggingDAO<D,F> implements DocDAO<D, F> {
    private static final Map<String, Set<Getter>> dict = new ConcurrentHashMap<>();

    @Autowired
    private Datastore datastore;

    static {
        Reflections reflections = new Reflections("com.sme.mts.data.document");
        for (Class<? extends  Data> clazz:reflections.getSubTypesOf(Data.class)) {
            Set<Getter> getters;
            dict.put(clazz.getName(), getters = new HashSet<>());
            try {
                for (PropertyDescriptor pd:Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
                    if( null == pd.getReadMethod() || "class".equals(pd.getName())) continue;
                    getters.add(new Getter(pd.getName(), pd.getReadMethod()));
                }
            } catch (IntrospectionException ex) {
                LogFactory.getLog("MorphiaDAO-static-block").error("Failed to get PropertyDescriptors",ex);
            }
        }
    }

    protected abstract Class<D> entityClass(F filter);

    /**
     * Fill Morphia {@link UpdateOperations} with field value of doc
     * @param uo
     * @param doc
     */
    protected void fillUO(UpdateOperations uo, D doc) {
        Set<Getter> getters = dict.get(doc.getClass().getName());
        getters.forEach(getter -> {
            try {
                Object v = getter.method.invoke(doc);
                if( null == v ) return;
                uo.set(getter.field, v);
            } catch (Throwable th) {
                logger.error("Failed to invoke getters:" + getter.field,th);
            }
        });
    }

    /**
     * Fill morphia {@link Query} with filter
     * @param query MongoDB query object
     * @param filter Filter
     * @return interrupted or not
     */
    protected boolean fillQ(Query query, F filter) {
        if( null == filter ) return true;

        if( null != filter.id ) query.field("id").equal(filter.id);
        if( null != filter.de ) query.field("de").equal(filter.de);
        filter.matches.forEach( (field,value) -> query.field(field).equal(value));
        return false;
    }


    private static final class Getter {
        private final String field;
        private final Method method;

        public Getter(String field, Method method) {
            this.field = field;
            this.method = method;
        }
    }


    @Override
    protected void doCreate(D data) {
        datastore.save(data);
    }

    @Override
    protected int doUpdate(F filter, D data) {
        Query<D> q = datastore.createQuery(entityClass(filter));
        fillQ(q,filter);

        UpdateOperations<D> uo = datastore.createUpdateOperations(entityClass(filter));
        fillUO(uo,data);

        UpdateResults results = datastore.update(q,uo);
        return results.getUpdatedCount();
    }

    @Override
    protected int doDelete(F filter) {
        Query<D> q = datastore.createQuery(entityClass(filter));
        fillQ(q,filter);

        WriteResult result = datastore.delete(q);
        return result.getN();
    }

    @Override
    protected List<D> doList(F filter) {
        Query<D> q = datastore.createQuery(entityClass(filter));
        fillQ(q,filter);
        return q.asList();
    }
}
