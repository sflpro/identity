package com.sflpro.identity.api.config.errorhandling;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Company: SFL LLC
 * Created on 8/26/16
 *
 * @author Samvel Abrahamyan
 */
@Provider
public class DtoValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = LoggerFactory.getLogger(DtoValidationExceptionMapper.class);

    @Context
    private HttpHeaders httpHeaders;

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        logger.debug("Dto validation exception occurred", exception);

        final StringBuilder message = new StringBuilder();
        exception.getConstraintViolations().forEach(e -> {
            String fieldName = e.getPropertyPath().toString();
            String msg = e.getMessage();
            message.append(", ").append(fieldName).append(" ").append(msg);
        });

        return ApplicationExceptionMapper.createResponse(
                new IdentityApiException(
                        IdentityApiError.INVALID_REQUEST_DATA,
                        message.toString(),
                        null
                ),
                httpHeaders.getMediaType()
        );
    }
}
