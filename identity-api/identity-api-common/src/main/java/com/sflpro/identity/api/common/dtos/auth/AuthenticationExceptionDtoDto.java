package com.sflpro.identity.api.common.dtos.auth;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;
import com.sflpro.identity.api.common.dtos.NonLoggableException;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class AuthenticationExceptionDtoDto extends IdentityApiExceptionDto implements NonLoggableException {

    public AuthenticationExceptionDtoDto(String message) {
        this(message, null);
    }

    public AuthenticationExceptionDtoDto(Exception cause) {
        this(null, cause);
    }

    public AuthenticationExceptionDtoDto(String message, Exception cause) {
        super(IdentityApiError.LOGIN_FAILED, message, cause);
    }
}
