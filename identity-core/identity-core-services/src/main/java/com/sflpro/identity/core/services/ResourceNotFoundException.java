package com.sflpro.identity.core.services;

/**
 * Company: SFL LLC
 * Created on 23/12/2017
 *
 * @author Davit Harutyunyan
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super("The specified resource was not found", cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
