package com.sme.mts.config;

import com.sme.mts.endpoint.Hello;
import com.sme.mts.extension.jersey.ResourceConfigEx;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.ws.rs.ApplicationPath;

@Configuration @Primary @ApplicationPath("v0")
public class JerseyApi extends ResourceConfigEx {
    public JerseyApi() {
        // scan("com.sme.mts.endpoint");
        register(Hello.class);
    }
}
