package com.sme.mts.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hello")
@Tag(name = "Useless")
public class Hello {
    @GET
    public String sayHello(){
        return "Hello, world";
    }
}
