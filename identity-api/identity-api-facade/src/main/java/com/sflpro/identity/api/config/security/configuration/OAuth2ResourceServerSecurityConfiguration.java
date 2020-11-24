package com.sflpro.identity.api.config.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

/**
 * Company: SFL LLC
 * Created on 09/06/2020
 *
 * @author Davit Harutyunyan
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2ResourceServerSecurityConfiguration.class);

    private final AuthenticationManagerResolver<HttpServletRequest> identityAuthenticationManagerResolver;
    private final CorsConfigurationSource corsConfigurationSource;

    public OAuth2ResourceServerSecurityConfiguration(@Qualifier("identityAuthenticationManagerResolver") final AuthenticationManagerResolver<HttpServletRequest> identityAuthenticationManagerResolver,
                                                     @Qualifier("corsConfigurationSource") final CorsConfigurationSource corsConfigurationSource) {
        this.identityAuthenticationManagerResolver = identityAuthenticationManagerResolver;
        this.corsConfigurationSource = corsConfigurationSource;
        logger.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/openapi.json").permitAll()
                                .antMatchers(HttpMethod.GET, "/status").permitAll()
                                .antMatchers(HttpMethod.PUT, "/identities/secret-reset/secret").permitAll()
                                .antMatchers(HttpMethod.PUT, "/identities/secret-reset/request-token").permitAll()
                                .antMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.authenticationManagerResolver(identityAuthenticationManagerResolver));
    }
}