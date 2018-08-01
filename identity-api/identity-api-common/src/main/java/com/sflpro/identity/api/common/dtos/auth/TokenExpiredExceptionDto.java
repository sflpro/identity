package com.sflpro.identity.api.common.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;
import com.sflpro.identity.api.common.dtos.NonLoggableException;

/**
 * Company: SFL LLC
 * Created on 7/19/2018
 *
 * @author Taron Petrosyan
 */
public class TokenExpiredExceptionDto extends IdentityApiExceptionDto implements NonLoggableException {
    public TokenExpiredExceptionDto(@JsonProperty("message") String message) {
        this(message, null);
    }

    public TokenExpiredExceptionDto(Exception cause) {
        this(null, cause);
    }

    public TokenExpiredExceptionDto(String message, Exception cause) {
        super(IdentityApiError.TOKEN_EXPIRED, message, cause);
    }
}
