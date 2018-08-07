package com.sme.mts.data.repository.mongodb;

import com.sme.mts.data.document.Metadata;
import com.sme.mts.data.repository.MetadataDAO;
import org.mongodb.morphia.query.Meta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Repository
public class MorphiaMetadataDAO extends MorphiaDAO<Metadata, MetadataDAO.Filter> implements MetadataDAO {
    @Override
    protected Class<Metadata> entityClass(MetadataDAO.Filter filter) {
        if( null == filter ) return Metadata.class;

        Class<? extends Metadata> clazz = Metadata.subclasses.get(filter.matches.get("catalog"));
        if( null != clazz ){
            return (Class<Metadata>)clazz;
        }

        return Metadata.class;
    }

    private void handleSubclasses(MetadataDAO.Filter filter, Consumer<MetadataDAO.Filter> consumer){
        if(null == filter) {
            filter = new MetadataDAO.Filter();
        }

        if(null != filter.matches.get(MetadataDAO.Filter.CATALOG)) {
            consumer.accept(filter);
        } else {
            boolean deleteMatch = !filter.matches.containsKey(MetadataDAO.Filter.CATALOG);

            // Adapting corresponding subclass
            for (String clazz : Metadata.subclasses.keySet()) {
                filter.matches.put(MetadataDAO.Filter.CATALOG, clazz);
                consumer.accept(filter);
            }

            filter.matches.put(MetadataDAO.Filter.CATALOG, Metadata.class.getSimpleName());
            consumer.accept(filter);

            if(deleteMatch) {
                filter.matches.remove(MetadataDAO.Filter.CATALOG);
            } else {
                filter.matches.put(MetadataDAO.Filter.CATALOG, null);
            }
        }
    }

    @Override
    public int update(MetadataDAO.Filter filter, Metadata data) {
        AtomicInteger c = new AtomicInteger(0);
        handleSubclasses(filter, f -> c.addAndGet(super.update(f, data)));
        return c.get();
    }

    @Override
    public List<Metadata> list(MetadataDAO.Filter filter) {
        List<Metadata> list = new ArrayList<>();
        handleSubclasses(filter, f -> list.addAll(super.list(f)));
        return list;
    }
}
