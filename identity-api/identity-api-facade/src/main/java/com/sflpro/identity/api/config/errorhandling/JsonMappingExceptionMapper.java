package com.sflpro.identity.api.config.errorhandling;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.sflpro.identity.api.common.dtos.IdentityApiErrorResponseDto;
import com.sflpro.identity.api.common.dtos.SerializationErrorResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 6/14/17
 *
 * @author Sevan Elyasian
 */
@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    private static final Logger logger = LoggerFactory.getLogger(JsonMappingExceptionMapper.class);

    @Context
    private HttpHeaders httpHeaders;

    public Response toResponse(final JsonMappingException exception) {
        logger.debug("Mapping JsonMappingException to SerializationErrorResponseDto...", exception);

        String message;
        if(exception instanceof PropertyBindingException) {
            message = (exception.getOriginalMessage() + ". " + ((PropertyBindingException) exception).getMessageSuffix()).trim();
        } else {
            message = exception.getOriginalMessage();
        }

        String messageSuffix = "";
        if(exception.getPath() != null && !exception.getPath().isEmpty()) {
            String path = exception.getPath().stream().map(e -> e.getFieldName() + ".").collect(Collectors.joining());
            messageSuffix += "path:" + path.substring(0, path.length() - 1);
        }

        if(exception.getLocation() != null) {
            messageSuffix += (StringUtils.isNotEmpty(messageSuffix) ? ", " : "")
                    + "line:" + exception.getLocation().getLineNr() + ", column:" + exception.getLocation().getColumnNr();
        }

        if(StringUtils.isNotEmpty(messageSuffix)) {
            message += " (" + messageSuffix + ")";
        }

        IdentityApiErrorResponseDto e = new SerializationErrorResponseDto(message);

        final Response result = Response.status(e.getResponseStatusCode())
                .entity(e)
                .type(httpHeaders.getMediaType())
                .build();

        logger.trace("Done mapping JsonMappingException to SerializationErrorResponseDto.");

        return result;
    }
}
