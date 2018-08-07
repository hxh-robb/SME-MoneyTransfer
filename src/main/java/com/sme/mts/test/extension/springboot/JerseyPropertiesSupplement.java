package com.sme.mts.test.extension.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * Add extension properties
 */
@ConfigurationProperties(prefix = "spring.jersey")
public class JerseyPropertiesSupplement {

    private boolean uriVersioning = false;

    private Set<String> resourcePackages;

    public boolean isUriVersioning() {
        return uriVersioning;
    }

    public void setUriVersioning(boolean uriVersioning) {
        this.uriVersioning = uriVersioning;
    }

    public Set<String> getResourcePackages() {
        return resourcePackages;
    }

    public void setResourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
    }
}
