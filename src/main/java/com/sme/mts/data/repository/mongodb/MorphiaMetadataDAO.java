package com.sme.mts.data.repository.mongodb;

import com.sme.mts.data.document.Metadata;
import com.sme.mts.data.repository.MetadataDAO;
import org.springframework.stereotype.Repository;

@Repository
public class MorphiaMetadataDAO extends MorphiaDAO<Metadata, MetadataDAO.Filter> implements MetadataDAO {
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
