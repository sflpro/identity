package com.sflpro.identity.api.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.WebApplicationException;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
@JsonIgnoreProperties({"response", "cause", "stackTrace", "responseStatusCode",
        "localizedMessage", "suppressed", "suppressedExceptions", "detailMessage"})
public class IdentityApiExceptionDto extends WebApplicationException {
    @JsonProperty("status")
    private final IdentityApiError error;

    @JsonProperty("httpStatus")
    private final int responseStatusCode;

    @JsonProperty("appErrorCode")
    private final long applicationErrorCode;

    @JsonProperty("message")
    private final String message;

    public IdentityApiExceptionDto(int responseStatusCode, String message, long applicationErrorCode) {
        this.error = null;
        this.message = message;
        this.responseStatusCode = responseStatusCode;
        this.applicationErrorCode = applicationErrorCode;
    }

    public IdentityApiExceptionDto(IdentityApiError error, Exception cause) {
        this(error, error.getDefaultMessage(), cause);
    }

    public IdentityApiExceptionDto(IdentityApiError error, String message, Exception cause) {
        this(error, message, error.getResponseHttpStatus().getStatusCode(), cause);
    }

    public IdentityApiExceptionDto(@JsonProperty("status") IdentityApiError error,
                                  @JsonProperty("message") String message,
                                  @JsonProperty("httpStatus") int responseStatusCode) {
        super(message, error.getResponseHttpStatus());
        this.applicationErrorCode = error.getErrorCode();
        this.message = message == null ? error.getDefaultMessage() == null ? error.toString() : error.getDefaultMessage() : message;
        this.responseStatusCode = responseStatusCode;
        this.error = error;
    }

    public IdentityApiExceptionDto(IdentityApiError error, String message, int responseStatusCode, Exception cause) {
        super(message, cause, error.getResponseHttpStatus());
        this.applicationErrorCode = error.getErrorCode();
        this.message = message == null ? error.getDefaultMessage() == null ? error.toString() : error.getDefaultMessage() : message;
        this.responseStatusCode = responseStatusCode;
        this.error = error;
    }

    public IdentityApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public long getApplicationErrorCode() {
        return applicationErrorCode;
    }
}