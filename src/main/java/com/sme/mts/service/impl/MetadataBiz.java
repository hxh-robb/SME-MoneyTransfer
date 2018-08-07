package com.sme.mts.service.impl;

import com.sme.mts.data.document.Metadata;
import com.sme.mts.data.repository.MetadataDAO;
import com.sme.mts.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataBiz implements MetadataService {
    @Autowired
    private MetadataDAO metadataDAO;

    @Override
    public List<Metadata> list(String catalog) {
        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.matches.put(MetadataDAO.Filter.CATALOG, catalog);
        return metadataDAO.list(filter);
    }
}
