package com.sme.mts.test.extension.springboot;

import javax.servlet.Registration;
import java.util.*;

/**
 * Wrapper class for batch registrations that fulfills the calling requirements of DynamicRegistrationBean
 * @param <D>
 */
public class RegistrationDynamics<D extends Registration.Dynamic> implements Registration.Dynamic {

    final List<Entry<D>> entries = new ArrayList<>();

    void add(D dynamic, String path, Map<String, String> initParams) {
        entries.add(new Entry<>(dynamic,path,initParams));
    }

    @Override
    public String getName() {
        String name = this.getClass().getSimpleName() + " {";
        boolean first = true;
        for (Entry<D> entry:entries) {
            if(first) {
                first = false;
            } else {
                name += ",";
            }

            name += null == entry.source.getName() ? "Unknown" : entry.source.getName() + "(" + entry.path + ")";
        }
        name += "}";
        return name;
    }

    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
        for (Entry<D> entry:entries) {
            entry.source.setAsyncSupported(isAsyncSupported);
        }
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
        Set<String> ret = new HashSet<>();
        for (Entry<D> entry:entries) {
            ret.addAll(entry.source.setInitParameters(entry.initParams));
        }
        return ret;
    }

    // No need to support below methods because DynamicRegistrationBean won't call them

    @Override
    public String getClassName() {
        throw new UnsupportedOperationException("No need to support");
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        throw new UnsupportedOperationException("No need to support");
    }

    @Override
    public String getInitParameter(String name) {
        throw new UnsupportedOperationException("No need to support");
    }

    @Override
    public Map<String, String> getInitParameters() {
        throw new UnsupportedOperationException("No need to support");
    }
}
