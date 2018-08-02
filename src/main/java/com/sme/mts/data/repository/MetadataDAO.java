package com.sme.mts.data.repository;

import com.sme.mts.data.document.Metadata;

public interface MetadataDAO extends DocDAO<Metadata,MetadataDAO.Filter> {
    class Filter extends DocDAO.Filter {
        public static final String CATALOG = "catalog";
    }
}
