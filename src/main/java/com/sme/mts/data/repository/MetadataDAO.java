package com.sme.mts.data.repository;

import com.sme.mts.data.document.Metadata;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public interface MetadataDAO extends DocDAO<Metadata,MetadataDAO.Filter> {
    Map<String,Class<? extends Metadata>> subclasses = new HashMap<String,Class<? extends Metadata>>(){
        {
            Reflections reflections = new Reflections(Metadata.class.getPackage().getName());
            reflections.getSubTypesOf(Metadata.class).forEach(c->this.put(c.getSimpleName(),c));
        }
    };

    class Filter extends DocDAO.Filter {
        public static final String CATALOG = "catalog";
    }
}
