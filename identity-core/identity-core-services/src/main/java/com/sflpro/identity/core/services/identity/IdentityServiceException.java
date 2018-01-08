package com.sflpro.identity.core.services.identity;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityServiceException extends RuntimeException {

    public IdentityServiceException() {
        super();
    }

    public IdentityServiceException(String message) {
        super(message);
    }

    public IdentityServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentityServiceException(Throwable cause) {
        super(cause);
    }

    public IdentityServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
