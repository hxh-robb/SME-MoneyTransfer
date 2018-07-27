package com.sme.mts.data.repository.mongodb;

import com.sme.mts.data.Data;
import com.sme.mts.data.document.Metadata;
import com.sme.mts.data.repository.DocDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.reflections.Reflections;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MorphiaDAOHelper<D extends Data> {
    private static final Log logger = LogFactory.getLog(MorphiaDAOHelper.class);
    private static final Map<String, Set<Getter>> dict = new ConcurrentHashMap<>();

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
                logger.error("Failed to get PropertyDescriptors",ex);
            }
        }

        System.out.println(dict.get(Metadata.class.getName()));
    }

    /**
     * Fill Morphia {@link UpdateOperations} with field value of doc
     * @param uo
     * @param doc
     */
    protected void fillUO(UpdateOperations uo, D doc) {
        Set<Getter> getters = dict.get(doc.getClass().getName());
        getters.forEach(getter -> {
            try {
                uo.set(getter.field, getter.method.invoke(doc));
            } catch (Throwable th) {
                logger.error("Failed to invoke getters",th);
            }
        });
    }

    /**
     * Fill morphia {@link Query} with filter
     * @param query MongoDB query object
     * @param filter Filter
     * @return interupted or not
     */
    protected boolean fillQ(Query query, DocDAO.Filter filter) {
        if( null == filter ) return true;

        if( null != filter.id ) query.field("id").equal(filter.id);
        if( null != filter.de ) query.field("de").equal(filter.de);
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
}
