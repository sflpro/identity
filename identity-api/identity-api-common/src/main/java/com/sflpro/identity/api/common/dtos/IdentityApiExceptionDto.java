package com.sflpro.identity.api.common.dtos;

import javax.ws.rs.WebApplicationException;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityApiExceptionDto extends WebApplicationException {
    private final IdentityApiError error;

    private final int responseStatusCode;

    private final long applicationErrorCode;

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

    public IdentityApiExceptionDto(IdentityApiError error,
                                   String message,
                                   int responseStatusCode) {
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