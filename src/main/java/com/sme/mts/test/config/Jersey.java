package com.sme.mts.test.config;

import com.sme.mts.test.extension.jersey.ResourceConfigEx;
import com.sme.mts.test.extension.springboot.JerseyPropertiesSupplement;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey RESTFul endpoint configuration
 */
@Primary @Configuration
@ApplicationPath("v0")
@EnableConfigurationProperties(JerseyPropertiesSupplement.class)
public class Jersey extends ResourceConfigEx {
    public Jersey(JerseyPropertiesSupplement properties){
        super(true, null == properties || null == properties.getResourcePackages() || properties.getResourcePackages().isEmpty());
        if(null != properties && null != properties.getResourcePackages() && !properties.getResourcePackages().isEmpty()){
            scan(properties.getResourcePackages().toArray(new String[]{}));
        }
    }
}