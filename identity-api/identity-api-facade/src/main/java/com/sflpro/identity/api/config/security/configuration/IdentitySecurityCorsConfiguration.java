package com.sflpro.identity.api.config.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 10/06/2020
 *
 * @author Davit Harutyunyan
 */
@Configuration
public class IdentitySecurityCorsConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(IdentitySecurityCorsConfiguration.class);

    private final Boolean allowAll;

    public IdentitySecurityCorsConfiguration(@Value("${identity.cors.allowAll}") final Boolean allowAll) {
        this.allowAll = allowAll;
        logger.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Bean("corsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource() {
        logger.debug("Initializing the corsConfigurationSource bean");
        final CorsConfiguration configuration = new CorsConfiguration();
        if (allowAll) {
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
            // setAllowCredentials(true) is important, otherwise:
            // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
            configuration.setAllowCredentials(Boolean.TRUE);
            // setAllowedHeaders is important! Without it, OPTIONS preflight request
            // will fail with 403 Invalid CORS request
            configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        }
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}