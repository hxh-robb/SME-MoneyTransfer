package com.sme.mts.extension.jersey;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * A ResourceConfig extension class to fix SpringBoot integration issue
 */
public class ResourceConfigEx extends ResourceConfig {
    public final ResourceConfig scan(String ... packages) {
        if (packages == null || packages.length == 0) {
            return this;
        }

        // TODO

        return this;
    }
}
