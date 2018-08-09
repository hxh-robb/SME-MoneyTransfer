package com.sme.mts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class Thymeleaf {
    @Bean
    public SpringResourceTemplateResolver jsonTemplateResovler(){
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setCheckExistence(true);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".json");
        resolver.setTemplateMode(TemplateMode.TEXT);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(1);
        resolver.setCheckExistence(true);
        return resolver;
    }
}
