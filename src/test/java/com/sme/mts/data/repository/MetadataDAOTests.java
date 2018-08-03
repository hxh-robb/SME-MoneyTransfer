package com.sme.mts.data.repository;

import com.sme.mts.RepositoryTestcase;
import com.sme.mts.data.document.Metadata;
import org.apache.commons.lang3.RandomUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mongodb.morphia.query.Meta;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MetadataDAOTests extends RepositoryTestcase<Metadata, MetadataDAO.Filter> {
    private static boolean init = false;
    private static final Map<String, Metadata> expected = new ConcurrentHashMap<>();

    private static final Class<? extends Metadata>[] metadataTypes = new Class[MetadataDAO.subclasses.size() + 1];
    static {
        metadataTypes[0] = Metadata.class;
        System.arraycopy(
            MetadataDAO.subclasses.values().toArray(),0,
            metadataTypes, 1, metadataTypes.length - 1
        );
    }

    @Autowired
    private MetadataDAO metadataDAO;

    @Override
    protected Map<String, Metadata> expected() {
        return expected;
    }

    @Override
    protected boolean isInitialized() {
        return init;
    }

    @Override
    protected void setInitialized(boolean bool) {
        init = bool;
    }

    @Override
    protected MetadataDAO dao() {
        return metadataDAO;
    }

    @Override
    protected Metadata newData() {
        try {
            return metadataTypes[RandomUtils.nextInt(0,metadataTypes.length)].newInstance();
        } catch (Throwable th) {
            throw new RuntimeException("Cannot new metadata instance",th);
        }
    }

    @Override
    protected MetadataDAO.Filter newFilter() {
        return new MetadataDAO.Filter();
    }

    @Override
    protected void setupData(Metadata data) {
    }

    @Override
    protected void setupModify(Metadata data) {

    }

    @Test
    public void t0_dummy_data(){
        generateDummyData(RandomUtils.nextInt(20,100));
    }
}
