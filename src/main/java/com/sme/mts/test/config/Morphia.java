package com.sme.mts.test.config;

import com.mongodb.MongoClient;
import com.sme.mts.test.extension.morphia.ValueMapperWithDynamicFieldsSupport;
import org.mongodb.morphia.Datastore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mongodb Morphia configuration
 */
@Configuration
public class Morphia {
    @Bean
    public Datastore datastore(MongoClient client) {
        org.mongodb.morphia.Morphia morphia = new org.mongodb.morphia.Morphia();

        morphia.mapPackage("com.sme.mts.data.document");
        morphia.getMapper().getOptions().setValueMapper(new ValueMapperWithDynamicFieldsSupport());

        Datastore datastore = morphia.createDatastore(client, "sme-mts");
        datastore.ensureIndexes(); // create database?
        return datastore;
    }
}
