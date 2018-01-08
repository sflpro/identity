package com.sflpro.identity.api.config.errorhandling;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiException;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.sflpro.identity.api.config.errorhandling.ApplicationExceptionMapper.createResponse;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundExceptionMapper.class);

    public ResourceNotFoundExceptionMapper() {
        super();
    }

    @Override
    public Response toResponse(final ResourceNotFoundException e) {
        logger.trace("Handling ResourceNotFoundException...");
        final Response response = createResponse(new IdentityApiException(IdentityApiError.NOT_FOUND, e.getMessage(), e), MediaType.APPLICATION_JSON_TYPE);
        logger.debug("Done handling ResourceNotFoundException.");
        return response;
    }
}
