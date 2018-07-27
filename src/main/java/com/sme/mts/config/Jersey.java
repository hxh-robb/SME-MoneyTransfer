package com.sme.mts.config;

import com.sme.mts.extension.jersey.ResourceConfigEx;
import com.sme.mts.extension.springboot.JerseyPropertiesSupplement;
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