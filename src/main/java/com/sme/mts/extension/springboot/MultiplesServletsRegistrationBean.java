package com.sme.mts.extension.springboot;

import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.Servlet;

/**
 * RegistrationBean for multiple Jersey servlets, to support multiple ResourceConfig
 */
public class MultiplesServletsRegistrationBean<T extends Servlet> extends ServletRegistrationBean<T> {
}
