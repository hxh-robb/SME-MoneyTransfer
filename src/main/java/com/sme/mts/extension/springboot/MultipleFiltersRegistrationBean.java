package com.sme.mts.extension.springboot;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.Filter;

/**
 * RegistrationBean for multiple Jersey filters, to support multiple ResourceConfig
 */
public class MultipleFiltersRegistrationBean<T extends Filter> extends FilterRegistrationBean<T> {

}
