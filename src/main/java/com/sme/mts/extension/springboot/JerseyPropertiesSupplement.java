package com.sme.mts.extension.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * Add extension properties
 */
@ConfigurationProperties(prefix = "spring.jersey")
public class JerseyPropertiesSupplement {

    private boolean uriVersioning = false;

    private List<String> endpointPackages;

    public boolean isUriVersioning() {
        return uriVersioning;
    }

    public void setUriVersioning(boolean uriVersioning) {
        this.uriVersioning = uriVersioning;
    }

    public List<String> getEndpointPackages() {
        return endpointPackages;
    }

    public void setEndpointPackages(List<String> endpointPackages) {
        this.endpointPackages = endpointPackages;
    }
}
