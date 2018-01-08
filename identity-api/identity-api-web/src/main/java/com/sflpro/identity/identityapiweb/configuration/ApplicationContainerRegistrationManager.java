package com.sflpro.identity.identityapiweb.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
@Configuration
public class ApplicationContainerRegistrationManager {

    @Value("${server.port}")
    private int serverPort;

    @Value("${server.display-name")
    private String serverDisplayName;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContainerRegistrationManager.class);

    @Bean
    public JettyServletWebServerFactory registerServletContainer() {
        logger.debug("Initializing Jetty embedded servlet container");
        final JettyServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();
        jettyServletWebServerFactory.setDisplayName(serverDisplayName);
        jettyServletWebServerFactory.setPort(serverPort);
        return jettyServletWebServerFactory;
        /*final JettyEmbeddedServletContainerFactory embeddedServletContainerFactory = new JettyEmbeddedServletContainerFactory();
        embeddedServletContainerFactory.setPort(serverPort);
        return embeddedServletContainerFactory;*/
    }
}
