package com.sflpro.identity.identityapiweb.configuration;

import com.sflpro.identity.api.IdentityJerseyConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Company: SFL LLC
 * Created on 6/23/16
 *
 * @author Yervand Aghababyan
 */
@Configuration
public class ServletRegistrationManager {

    @Value("${server.servlet.context-path}")
    private String serverServletContextPath;

    @Bean
    public ServletRegistrationBean<ServletContainer> jerseyServlet() {
        // Create registration bean
        final ServletRegistrationBean<ServletContainer> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new ServletContainer());
        servletRegistrationBean.setName("servlet.jersey");
        servletRegistrationBean.getUrlMappings().add(serverServletContextPath + "/*");
        servletRegistrationBean.setOrder(0);
        servletRegistrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, IdentityJerseyConfig.class.getName());

        return servletRegistrationBean;
    }
}


