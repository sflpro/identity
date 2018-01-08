package com.sflpro.identity.core.services.token;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public abstract class TokenServiceException extends Exception {
    public TokenServiceException() {
    }

    public TokenServiceException(String message) {
        super(message);
    }

    public TokenServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenServiceException(Throwable cause) {
        super(cause);
    }

    public TokenServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
