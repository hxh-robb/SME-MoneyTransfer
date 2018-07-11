package com.sme.mts.config;

import com.sme.mts.endpoint.NewHello;
import com.sme.mts.extension.jersey.ResourceConfigEx;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration @ApplicationPath("v1")
public class JerseyApi2 extends ResourceConfigEx {
    public JerseyApi2() {
        // scan("com.sme.mts.endpoint");
        register(NewHello.class);
    }
}
