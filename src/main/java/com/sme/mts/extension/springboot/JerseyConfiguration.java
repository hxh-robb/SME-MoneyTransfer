package com.sme.mts.extension.springboot;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;
import javax.ws.rs.ApplicationPath;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnExpression("'${spring.jersey.applicationPath:}' != '' and ${spring.jersey.uriVersioning:false}")
@EnableConfigurationProperties(JerseyProperties.class)
public class JerseyConfiguration {
    private final JerseyProperties jersey;
    private final List<ResourceConfig> configs = new ArrayList<>();
    private final List<ResourceConfigCustomizer> customizers;

    private Map<ResourceConfig, String> paths = new HashMap<>();

    public JerseyConfiguration(
        JerseyProperties jersey,
        ResourceConfig[] configs,
        ObjectProvider<List<ResourceConfigCustomizer>> customizers) {

        this.jersey = jersey;
        this.configs.addAll(Arrays.asList(configs));
        this.customizers = customizers.getIfAvailable();
    }

    @PostConstruct
    public void path(){
        replacePrimaryConfig();
        resolveApplicationPaths();
        customize();
    }

    private void replacePrimaryConfig(){
        List<ResourceConfig> filtered = this.configs.stream().filter(config -> null != AnnotationUtils.findAnnotation(config.getApplication().getClass(), Primary.class)).collect(Collectors.toList());
        if(!filtered.isEmpty()){
            this.configs.removeAll(filtered);
            for (ResourceConfig config:filtered) {
                try {
                    Object primaryDouble = null;
                    for (Constructor constructor:config.getClass().getConstructors()) {
                        primaryDouble = constructor.newInstance(new Object[constructor.getParameterCount()]);
                        break;
                    }

                    if( primaryDouble instanceof  ResourceConfig ){
                        this.configs.add((ResourceConfig)primaryDouble);
                    }
                } catch (Throwable th) {
                    // Ignore
                    th.printStackTrace();
                }
            }
        }
    }

    private void resolveApplicationPaths() {
        for (ResourceConfig config:configs) {
            String path = findApplicationPath(AnnotationUtils.findAnnotation(
                config.getApplication().getClass(), ApplicationPath.class)
            );
            path = jersey.getApplicationPath() + path;
            paths.put(config,path);
        }
    }

    private void customize() {
        if (this.customizers != null) {
            AnnotationAwareOrderComparator.sort(this.customizers);
            for (ResourceConfigCustomizer customizer : this.customizers) {
                for (ResourceConfig config:configs) {
                    customizer.customize(config);
                }
            }
        }
    }

//    @Bean @ConditionalOn
//    public ResourceConfig application(){
//        return null;
//    }

    @Bean
    @ConditionalOnMissingBean(name = "multipleJerseyFiltersRegistration")
    @ConditionalOnProperty(prefix = "spring.jersey", name = "type", havingValue = "filter")
    public RegistrationBean multipleJerseyFiltersRegistration(){
        MultipleFiltersRegistrationBean<ServletContainer> registration = new MultipleFiltersRegistrationBean<>();
        for (ResourceConfig config:configs) {
            String path = paths.get(config);
            Map<String, String> initParams = new LinkedHashMap<>();
            initParams.put(ServletProperties.FILTER_CONTEXT_PATH,stripPattern(path));
            jersey.getInit().forEach(initParams::put);
            registration.addFilter(new ServletContainer(config),path,initParams);
        }
        registration.setOrder(jersey.getFilter().getOrder() - 2);
        registration.addUrlPatterns(paths.values().toArray(new String[]{}));
        registration.setName("multipleJerseyFilters");
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(name = "multipleJerseyServletsRegistration")
    @ConditionalOnProperty(prefix = "spring.jersey", name = "type", havingValue = "servlet", matchIfMissing = true)
    public RegistrationBean multipleJerseyServletsRegistration(){
        MultipleServletsRegistrationBean<ServletContainer> registration = new MultipleServletsRegistrationBean<>();
        for (ResourceConfig config:configs) {
            String path = paths.get(config);
            Map<String, String> initParams = new LinkedHashMap<>();
            jersey.getInit().forEach(initParams::put);
            registration.addServlet(new ServletContainer(config), path, initParams);
        }
        registration.setName("multipleJerseyServlets");
        registration.setLoadOnStartup(this.jersey.getServlet().getLoadOnStartup());
        return registration;
    }

    // Below methods are copy from JerseyAutoConfiguration

    private String stripPattern(String path) {
        if (path.endsWith("/*")) {
            path = path.substring(0, path.lastIndexOf("/*"));
        }
        return path;
    }

    private static String findApplicationPath(ApplicationPath annotation) {
        // Jersey doesn't like to be the default servlet, so map to /* as a fallback
        if (annotation == null) {
            return "/*";
        }
        return parseApplicationPath(annotation.value());
    }

    private static String parseApplicationPath(String applicationPath) {
        if (!applicationPath.startsWith("/")) {
            applicationPath = "/" + applicationPath;
        }
        return (applicationPath.equals("/") ? "/*" : applicationPath + "/*");
    }
}
