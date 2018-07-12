package com.sme.mts.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("metadata")
public class Metadata {
    @GET @Tag(name="Common")
    public String todo(){
        return "TODO";
    }
}
