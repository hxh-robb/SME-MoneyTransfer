package com.sme.mts.test.extension.springboot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.*;
import javax.servlet.ServletRegistration.Dynamic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * RegistrationBean for multiple Jersey servlets, to support multiple ResourceConfig
 */
public class MultipleServletsRegistrationBean<T extends Servlet> extends ServletRegistrationBean<T> {
    private static final Log logger = LogFactory.getLog(MultipleServletsRegistrationBean.class);

    // A useless servlet for passing super constructor not null validation
    private static final Servlet USELESS = new GenericServlet() {
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {}
    };

    private final List<Entry<Servlet>> entries = new ArrayList<>();

    public void addServlet(Servlet servlet, String path, Map<String, String> initParams){
        entries.add(new Entry(servlet,path,initParams));
    }

    @Override
    public String getServletName() {
        String ret = "{";
        boolean first = true;
        for (Entry<Servlet> entry:entries) {
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
    protected String getDescription() {
        return "servlet " + getServletName();
    }

    @Override
    protected Dynamic addRegistration(String description, ServletContext servletContext) {
        ServletRegistrationDynamics dynamics = new ServletRegistrationDynamics();
        for (Entry<Servlet> entry:entries) {
            String name = getOrDeduceName(entry.source) + entry.path.replaceAll("/\\*","").replaceAll("/","_");
            logger.info("Servlet " + name + " mapped to " + entry.path);
            dynamics.add(servletContext.addServlet(name, entry.source), entry.path, entry.initParams);
        }
        return dynamics;
    }

    /**
     * Configure registration settings. Subclasses can override this method to perform
     * additional configuration if required.
     * @param registration the registration
     */
    @Override
    protected void configure(ServletRegistration.Dynamic registration) {
        super.configure(registration);

        if(registration instanceof ServletRegistrationDynamics){
            registration.addMapping();
        }

        registration.setLoadOnStartup(this.loadOnStartup);
        if (this.multipartConfig != null) {
            registration.setMultipartConfig(this.multipartConfig);
        }
    }

    @Override
    protected T getServlet() {
        // This RegistrationBean is not for single Servlet!
        return null;
    }

    // Below fields and methods are copy from ServletRegistrationBean

    private int loadOnStartup = -1;

    private MultipartConfigElement multipartConfig;

    public MultipleServletsRegistrationBean(String ... urlMappings) {
        this(true, urlMappings);
    }

    public MultipleServletsRegistrationBean(boolean alwaysMapUrl, String ... urlMappings) {
        super((T)USELESS, alwaysMapUrl, urlMappings);
    }

    /**
     * Sets the {@code loadOnStartup} priority. See
     * {@link ServletRegistration.Dynamic#setLoadOnStartup} for details.
     * @param loadOnStartup if load on startup is enabled
     */
    public void setLoadOnStartup(int loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /**
     * Set the {@link MultipartConfigElement multi-part configuration}.
     * @param multipartConfig the multi-part configuration to set or {@code null}
     */
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {
        this.multipartConfig = multipartConfig;
    }

    /**
     * Returns the {@link MultipartConfigElement multi-part configuration} to be applied
     * or {@code null}.
     * @return the multipart config
     */
    public MultipartConfigElement getMultipartConfig() {
        return this.multipartConfig;
    }
}
