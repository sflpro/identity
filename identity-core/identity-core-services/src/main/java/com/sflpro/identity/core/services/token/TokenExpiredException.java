package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.services.auth.AuthenticationServiceException;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenExpiredException extends AuthenticationServiceException {

    private static final String MESSAGE_FORMAT = "%s is expired";

    public TokenExpiredException(String token) {
        super(String.format(MESSAGE_FORMAT, token));
    }

}