package com.sflpro.identity.api.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Company: SFL LLC
 * Created on 11/29/17
 *
 * @author Yervand Aghababyan
 */
public class IdentityApiErrorResponseDto {

    @JsonProperty("status")
    private IdentityApiError error;

    @JsonProperty("httpStatus")
    private int responseStatusCode;

    @JsonProperty("appErrorCode")
    private long applicationErrorCode;

    @JsonProperty("message")
    private String message;

    public IdentityApiErrorResponseDto() {
        super();
    }

    public IdentityApiErrorResponseDto(
            final IdentityApiError error,
            final int responseStatusCode,
            final long applicationErrorCode,
            final String message
    ) {
        this();

        this.error = error;
        this.responseStatusCode = responseStatusCode;
        this.applicationErrorCode = applicationErrorCode;
        this.message = message;
    }

    public IdentityApiErrorResponseDto(
            final int responseStatusCode,
            final long applicationErrorCode,
            final String message
    ) {
        this(null, responseStatusCode, applicationErrorCode, message);
    }

    public IdentityApiErrorResponseDto(final IdentityApiError error) {
        this(error, error.getDefaultMessage());
    }

    public IdentityApiErrorResponseDto(
            final IdentityApiError error,
            final String message
    ) {
        this(error, error.getResponseHttpStatus().getStatusCode(), message);
    }

    public IdentityApiErrorResponseDto(
            final IdentityApiError error,
            final int responseStatusCode,
            final String message
    ) {
        this();

        this.error = error;
        this.responseStatusCode = responseStatusCode;
        this.applicationErrorCode = error.getErrorCode();
        if (message == null || message.isEmpty()) {
            this.message = error.getDefaultMessage() == null ? error.toString() : error.getDefaultMessage();
        } else {
            this.message = message;
        }
    }

    public IdentityApiError getError() {
        return error;
    }

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

