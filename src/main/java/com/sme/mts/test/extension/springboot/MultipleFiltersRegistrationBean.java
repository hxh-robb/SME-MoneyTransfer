package com.sme.mts.test.extension.springboot;

import org.springframework.boot.web.servlet.MyAbstractFilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RegistrationBean for multiple Jersey filters, to support multiple ResourceConfig
 */
public final class MultipleFiltersRegistrationBean<T extends Filter> extends MyAbstractFilterRegistrationBean<T> {

    private final List<Entry<Filter>> entries = new ArrayList<>();

    public void addFilter(Filter filter, String path, Map<String, String> initParams){
        entries.add(new Entry(filter,path,initParams));
    }

    public MultipleFiltersRegistrationBean(ServletRegistrationBean<?>... servletRegistrationBeans) {
        super(servletRegistrationBeans);
    }

    @Override
    protected String getDescription() {
        String ret = "filters {";
        boolean first = true;
        for (Entry<Filter> entry:entries) {
            if(first) {
                first = false;
            } else {
                ret += ",";
            }

            ret += getOrDeduceName(entry.source);
        }
        ret += "}";
        return ret;
    }

    @Override
    protected Dynamic addRegistration(String description, ServletContext servletContext) {
        FilterRegistrationDynamics dynamics = new FilterRegistrationDynamics();
        for (Entry<Filter> entry:entries) {
            FilterRegistration.Dynamic dynamic = servletContext.addFilter(getOrDeduceName(entry.source) + entry.path.replaceAll("/\\*","").replaceAll("/","_"), entry.source);
            dynamics.add(dynamic, entry.path, entry.initParams);
        }
        return dynamics;
    }

    @Override
    public T getFilter() {
        throw new UnsupportedOperationException("This RegistrationBean is not for single Filter");
    }
}
