package com.sflpro.identity.api.common.dtos.auth;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiException;
import com.sflpro.identity.api.common.dtos.NonLoggableException;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class AuthenticationExceptionDto extends IdentityApiException implements NonLoggableException {

    public AuthenticationExceptionDto(String message) {
        this(message, null);
    }

    public AuthenticationExceptionDto(Exception cause) {
        this(null, cause);
    }

    public AuthenticationExceptionDto(String message, Exception cause) {
        super(IdentityApiError.LOGIN_FAILED, message, cause);
    }
}
