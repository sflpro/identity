package com.sflpro.identity.api.config.security.configuration;

import com.sflpro.identity.api.config.security.token.converter.JwtBearerIdentityTokenAuthenticationCommonConverter;
import com.sflpro.identity.api.config.security.token.parser.JwtParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Company: SFL LLC
 * Created on 10/06/2020
 *
 * @author Davit Harutyunyan
 */
@Configuration
public class IdentityAuthenticationManagerResolverConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(IdentityAuthenticationManagerResolverConfiguration.class);

    private final String sflIdentityJwtIssuer;
    private final String sflIdentityJwkSetUri;
    private final JwtParser jwtParser;

    public IdentityAuthenticationManagerResolverConfiguration(@Value("${spring.security.oauth2.sflidentity.jwt.issuer}") final String sflIdentityJwtIssuer,
                                                              @Value("${spring.security.oauth2.sflidentity.jwt.jwk-set-uri}") final String sflIdentityJwkSetUri,
                                                              @Qualifier("identityJwtParser") final JwtParser jwtParser) {
        this.sflIdentityJwtIssuer = sflIdentityJwtIssuer;
        this.sflIdentityJwkSetUri = sflIdentityJwkSetUri;
        this.jwtParser = jwtParser;
        logger.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Bean("identityAuthenticationManagerResolver")
    public AuthenticationManagerResolver<HttpServletRequest> identityAuthenticationManagerResolver() {
        logger.debug("Initializing the identityAuthenticationManagerResolver bean");
        final Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();
        authenticationManagers.put(sflIdentityJwtIssuer, identityAuthenticationManager());
        return new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);
    }

    @Bean("identityAuthenticationManager")
    public AuthenticationManager identityAuthenticationManager() {
        logger.debug("Initializing the identityAuthenticationManager bean");
        final JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(this.sflIdentityJwkSetUri).build();
        final JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        authenticationProvider.setJwtAuthenticationConverter(new JwtBearerIdentityTokenAuthenticationCommonConverter(jwtParser));
        return authenticationProvider::authenticate;
    }
}