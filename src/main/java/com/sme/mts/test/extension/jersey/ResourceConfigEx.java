package com.sme.mts.test.extension.jersey;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.reflections.Reflections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * A ResourceConfig extension class to fix SpringBoot integration issue and add swagger support
 */
public class ResourceConfigEx extends ResourceConfig {
    private static final Log logger = LogFactory.getLog(ResourceConfigEx.class);

    protected ResourceConfigEx(boolean enableSwagger) {
        this(enableSwagger, false);
    }

    protected ResourceConfigEx(boolean enableSwagger, boolean scanFromSwagger) {
        if(enableSwagger) {
            register(OpenApiResource.class);
            register(SwaggerWelcome.class); // swagger-ui index page redirection

            if(scanFromSwagger){
                try {
                    Set<String> packages = new JaxrsOpenApiContextBuilder().buildContext(true).getOpenApiConfiguration().getResourcePackages();
                    scan(packages.toArray(new String[]{}));
                } catch (OpenApiConfigurationException e) {
                    logger.error("No resource package is specified", e);
                }
            }
        }
    }

    public final ResourceConfig scan(String ... packages) {
        if (packages == null || packages.length == 0) {
            return this;
        }

        for (String pack : packages) {
            Reflections reflections = new Reflections(pack);

            Set<Class<?>> paths = reflections.getTypesAnnotatedWith(Path.class);
            Set<Class<?>> duplicatedPaths = new HashSet<>();
            for (Class<?> path:paths) {
                if(null != path.getAnnotation(Path.class)) {
                    continue;
                }

                do {
                    path = path.getSuperclass();
                    duplicatedPaths.add(path);
                } while(null == path.getAnnotation(Path.class));
            }
            paths.removeAll(duplicatedPaths);
            paths.parallelStream().forEach(this::register);
        }

        return this;
    }

    @Path("/") @Hidden
    public static final class SwaggerWelcome {
        @GET
        public Response redirect(@Context UriInfo uriInfo) throws URISyntaxException {
            URI uri = new URI("/webjars/swagger-ui/index.html?url=" + uriInfo.getRequestUri().getPath() + "/openapi.yaml&validatorUrl=");
            return Response.seeOther(uri).build();
        }
    }
}
