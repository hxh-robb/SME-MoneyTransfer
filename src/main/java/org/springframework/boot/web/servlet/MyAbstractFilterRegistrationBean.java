package org.springframework.boot.web.servlet;

import javax.servlet.Filter;

/**
 * This class is the reason why I hate Spring framework
 * @param <T>
 */
public abstract class MyAbstractFilterRegistrationBean<T extends Filter> extends AbstractFilterRegistrationBean<T> {
    /**
     * Just try to make the AbstractFilterRegistrationBean constructor public
     * @param servletRegistrationBeans
     */
    public MyAbstractFilterRegistrationBean(ServletRegistrationBean<?>... servletRegistrationBeans){
        super(servletRegistrationBeans);
    }
}
