package com.sme.mts.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("goodbye")
@Tag(name = "Useless")
public class Hello2 {
    @GET
    public String sayGoodbye(){
        return "Bye, world";
    }
}
