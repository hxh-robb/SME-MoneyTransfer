package com.sme.mts.test.extension.springboot;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.Collection;
import java.util.EnumSet;

/**
 * Wrapper class for batch registrations that fulfills the calling requirements of DynamicRegistrationBean & AbstractFilterRegistrationBean
 */
public class FilterRegistrationDynamics extends RegistrationDynamics<FilterRegistration.Dynamic> implements FilterRegistration.Dynamic {
    @Override
    public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {
        for (Entry<FilterRegistration.Dynamic> entry:entries) {
            entry.source.addMappingForUrlPatterns(dispatcherTypes, isMatchAfter, entry.path);
        }
    }

    // No need to support below methods because DynamicRegistrationBean & AbstractFilterRegistrationBean won't call them

    @Override
    public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames) {
        throw new UnsupportedOperationException("No need to support");
    }

    @Override
    public Collection<String> getServletNameMappings() {
        throw new UnsupportedOperationException("No need to support");
    }

    @Override
    public Collection<String> getUrlPatternMappings() {
        throw new UnsupportedOperationException("No need to support");
    }
}
