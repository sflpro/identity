package com.sflpro.identity.api.config.errorhandling;

import com.fasterxml.jackson.core.JsonParseException;
import com.sflpro.identity.api.common.dtos.IdentityApiErrorResponseDto;
import com.sflpro.identity.api.common.dtos.SerializationErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Company: SFL LLC
 * Created on 6/14/17
 *
 * @author Sevan Elyasian
 */
@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    private static final Logger logger = LoggerFactory.getLogger(JsonParseExceptionMapper.class);

    private static final String MESSAGE_WITH_LOCATION = "Json parse error on line:%s, column:%s. %s.";
    private static final String MESSAGE_WITHOUT_LOCATION = "Json parse error. %s.";

    @Context
    private HttpHeaders httpHeaders;

    public Response toResponse(final JsonParseException exception) {
        logger.debug("Mapping JsonParseException to SerializationErrorResponseDto...", exception);

        String message;
        if(exception.getLocation() != null) {
            message = String.format(MESSAGE_WITH_LOCATION, exception.getLocation().getLineNr(), exception.getLocation().getColumnNr(), exception.getOriginalMessage());

        } else {
            message = String.format(MESSAGE_WITHOUT_LOCATION, exception.getOriginalMessage());
        }

        IdentityApiErrorResponseDto e = new SerializationErrorResponseDto(message);

        final Response result = Response.status(e.getResponseStatusCode())
                .entity(e)
                .type(httpHeaders.getMediaType())
                .build();

        logger.trace("Done mapping JsonParseException to SerializationErrorResponseDto.");

        return result;
    }
}
