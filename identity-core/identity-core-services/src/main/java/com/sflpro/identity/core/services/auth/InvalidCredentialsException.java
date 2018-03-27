package com.sflpro.identity.core.services.auth;

/**
 * Company: SFL LLC
 * Created on 26/03/2018
 *
 * @author Davit Harutyunyan
 */
public class InvalidCredentialsException extends AuthenticationServiceException {

    private static final String MESSAGE = "Invalid credentials";

    public InvalidCredentialsException() {
        super(MESSAGE);
    }
}
