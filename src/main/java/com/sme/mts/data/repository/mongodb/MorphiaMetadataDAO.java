package com.sme.mts.data.repository.mongodb;

import com.sme.mts.data.document.Metadata;
import com.sme.mts.data.repository.MetadataDAO;
import org.reflections.Reflections;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MorphiaMetadataDAO extends MorphiaDAO<Metadata, MetadataDAO.Filter> implements MetadataDAO {
    private static final Map<String,Class<? extends Metadata>> subclasses = new HashMap<>();
    static {
        Reflections reflections = new Reflections(Metadata.class.getPackage().getName());
        reflections.getSubTypesOf(Metadata.class).forEach(c->subclasses.put(c.getSimpleName(),c));
    }

    @Override
    protected Class<Metadata> entityClass(MetadataDAO.Filter filter) {
        if( null == filter ) return Metadata.class;

        Class<? extends Metadata> clazz = subclasses.get(filter.matches.get("catalog"));
        if( null != clazz ){
            return (Class<Metadata>)clazz;
        }

        return Metadata.class;
    }
}
