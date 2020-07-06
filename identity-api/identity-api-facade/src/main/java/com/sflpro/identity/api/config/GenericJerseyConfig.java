package com.sflpro.identity.api.config;

import com.sflpro.identity.api.config.errorhandling.ApplicationExceptionMapper;
import com.sflpro.identity.api.config.errorhandling.DtoValidationExceptionMapper;
import com.sflpro.identity.api.config.errorhandling.JsonMappingExceptionMapper;
import com.sflpro.identity.api.config.errorhandling.JsonParseExceptionMapper;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.glassfish.jersey.server.validation.ValidationFeature;


/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class GenericJerseyConfig extends ResourceConfig {

    public GenericJerseyConfig() {

        // Configurations
        register(JacksonJsonProvider.class);
        register(ValidationFeature.class);

        // Exception mappers
        register(ApplicationExceptionMapper.class);
        register(DtoValidationExceptionMapper.class);
        register(JsonParseExceptionMapper.class);
        register(JsonMappingExceptionMapper.class);
        
        // Swagger specific settings
        register(OpenApiResource.class);
        
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, false);
        property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);

        EncodingFilter.enableFor(this, GZipEncoder.class);
    }
}
