package com.sflpro.identity.api;

import com.sflpro.identity.api.config.GenericJerseyConfig;
import com.sflpro.identity.api.config.errorhandling.ResourceNotFoundExceptionMapper;
import com.sflpro.identity.api.endpoints.*;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityJerseyConfig extends GenericJerseyConfig {
    public IdentityJerseyConfig() {
        super();

        // Exception Mappers
        register(ResourceNotFoundExceptionMapper.class);

        // Endpoints
        register(AuthenticationEndpoint.class);
        register(IdentityEndpoint.class);
        register(PrincipalEndpoint.class);
        register(PermissionEndpoint.class);
        register(RoleEndpoint.class);
        register(StatusEndpoint.class);
        register(PermissionEndpoint.class);
        register(ResourceEndpoint.class);

        // Swagger specific settings
        register(AcceptHeaderOpenApiResource.class);
        
        // Filters
        register(IdentityJerseyConfig.class);
    }
}
