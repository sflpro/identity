package com.sflpro.identity.api.config;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path}")
    private String mountPath;

    @Bean
    public BeanConfig getSwaggerConfig() {
        final String version = getClass().getPackage().getImplementationVersion();

        final BeanConfig beanConfig = new BeanConfig();

        beanConfig.setBasePath(mountPath);

        beanConfig.setVersion(version);

        beanConfig.setResourcePackage("com.sflpro.identity.api.endpoints");
        beanConfig.setScan(true);

        final Info info = new Info();

        info.setTitle("Weadapt Internal API Documentation");
        info.setDescription("Description of API calls and their parameters");
        info.setContact(
                new Contact()
                        .name("Weadapt")
                        .email("info@weadapt.eu")
                        .url("https://weadapt.digital/contact/")
        );
        info.version(version);

        beanConfig.setInfo(info);

        return beanConfig;
    }
}
