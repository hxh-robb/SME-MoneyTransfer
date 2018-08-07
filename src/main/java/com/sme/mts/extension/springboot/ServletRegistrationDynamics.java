package com.sme.mts.extension.springboot;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper class for batch registrations that meets the calling requirements of DynamicRegistrationBean & ServletRegistrationBean
 */
public class ServletRegistrationDynamics extends RegistrationDynamics<ServletRegistration.Dynamic> implements ServletRegistration.Dynamic {

    @Override
    public Set<String> addMapping(String... urlPatterns) {
        Set<String> ret = new HashSet<>();
        for (Entry<ServletRegistration.Dynamic> entry:entries) {
            ret.addAll(entry.source.addMapping(entry.path));
        }
        return ret;
    }

    @Override
    public void setLoadOnStartup(int loadOnStartup) {
        for (Entry<ServletRegistration.Dynamic> entry:entries) {
            entry.source.setLoadOnStartup(loadOnStartup);
        }
    }

    @Override
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {
        for (Entry<ServletRegistration.Dynamic> entry:entries) {
            entry.source.setMultipartConfig(multipartConfig);
        }
    }

    // No need to support below methods because DynamicRegistrationBean & ServletRegistrationBean won't call them

    @Override
    public Set<String> setServletSecurity(ServletSecurityElement constraint) {
        return null;
    }

    @Override
    public void setRunAsRole(String roleName) {

    }

    @Override
    public Collection<String> getMappings() {
        return null;
    }

    @Override
    public String getRunAsRole() {
        return null;
    }
}
