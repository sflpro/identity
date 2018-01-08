package com.sflpro.identity.core.services.auth;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class AuthenticationServiceException extends Exception {

    public AuthenticationServiceException() {
        super();
    }

    public AuthenticationServiceException(String msg) {
        this(msg, null);
    }

    public AuthenticationServiceException(Exception cause) {
        this(null, cause);
    }

    public AuthenticationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
