package com.sflpro.identity.api.common.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;
import com.sflpro.identity.api.common.dtos.NonLoggableException;

/**
 * Company: SFL LLC
 * Created on 8/31/2018
 *
 * @author Taron Petrosyan
 */
public class AuthenticationAttemptLimitReachedExceptionDto extends IdentityApiExceptionDto implements NonLoggableException {
    public AuthenticationAttemptLimitReachedExceptionDto(@JsonProperty("message") String message) {
        this(message, null);
    }

    public AuthenticationAttemptLimitReachedExceptionDto(Exception cause) {
        this(null, cause);
    }

    public AuthenticationAttemptLimitReachedExceptionDto(String message, Exception cause) {
        super(IdentityApiError.AUTH_LIMIT_REACHED, message, cause);
    }
}
