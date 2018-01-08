package com.sflpro.identity.api.config.errorhandling;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.sflpro.identity.api.common.dtos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * User: Yervand Aghababyan
 * Company: SFL LLC
 * Date: 5/12/2014
 * Time: 11:38 AM
 */
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionMapper.class);

    @Context
    private HttpHeaders httpHeaders;

    @Override
    public Response toResponse(Exception e) {
        logger.debug("Mapping exception", e);

        if (!(e instanceof NonLoggableException)) {
            logger.error("Unexpected error occurred!", e);
        }

        final IdentityApiException apiException;
        if (e instanceof IdentityApiException) {
            apiException = (IdentityApiException) e;
        } else if (e instanceof JsonMappingException) {
            apiException = new InvalidRequestException(e.getMessage(), e);
        } /*else if (e instanceof AccessDeniedException) {
            apiException = new UnauthorizedIdentityException("Identity is not authorized", e);
        } */ else if (e instanceof WebApplicationException) {
            /*if (e instanceof ForbiddenException) {
                apiException = new ForbiddenCustomerRequestException(e.getMessage(), e);
            } else if (e instanceof BadRequestException && e.getCause() instanceof UnmarshalException) {
                // Handle XML parsing exceptions
                apiException = parseUnmarshalExceptions((UnmarshalException) e.getCause());
            } else*/
            if (((WebApplicationException) e).getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                apiException = new ResourceNotFoundDto(e.getMessage(), e);
            } else {
                apiException = new IdentityApiException(IdentityApiError.UNEXPECTED_INTERNAL_ERROR, e.getMessage(), ((WebApplicationException) e).getResponse().getStatus(), e);
            }
        } else {
            apiException = new IdentityApiException(IdentityApiError.UNEXPECTED_INTERNAL_ERROR, e.getMessage(), e);
        }

        return createResponse(apiException, httpHeaders.getMediaType());
    }

    /*private IdentityApiException parseUnmarshalExceptions(UnmarshalException e) {

        IdentityApiException apiException;

        if (e.getLinkedException() != null
                && e.getCause() instanceof UnmarshalException
                && ((UnmarshalException) e.getCause()).getLinkedException().getCause() instanceof IllegalArgumentException) {
            final IllegalArgumentException cause = (IllegalArgumentException) e.getLinkedException().getCause();

            apiException = new IdentityApiException(IdentityApiError.INVALID_REQUEST_DATA, cause.getMessage(), e);
        } else if (e.getLinkedException() != null
                && e.getCause() instanceof SAXParseException) {
            final SAXParseException cause = (SAXParseException) e.getCause();

            apiException = new IdentityApiException(IdentityApiError.INVALID_REQUEST_DATA, cause.getMessage(), e);
        } else if (e.getLinkedException() != null
                && e.getLinkedException() instanceof SAXParseException) {
            final SAXParseException cause = (SAXParseException) e.getLinkedException();

            apiException = new IdentityApiException(IdentityApiError.INVALID_REQUEST_DATA, cause.getMessage(), e);
        } else {

            apiException = new IdentityApiException(IdentityApiError.INVALID_REQUEST_DATA, e.getMessage(), e);
        }

        return apiException;
    }*/

    public static Response createResponse(final IdentityApiException exception, final MediaType mediaType) {
        return Response.status(exception.getResponseStatusCode())
                .entity(
                        new IdentityApiErrorResponseDto(
                                exception.getError(),
                                exception.getResponseStatusCode(),
                                exception.getApplicationErrorCode(),
                                exception.getMessage()
                        )
                )
                .type(mediaType)
                .build();
    }
}
