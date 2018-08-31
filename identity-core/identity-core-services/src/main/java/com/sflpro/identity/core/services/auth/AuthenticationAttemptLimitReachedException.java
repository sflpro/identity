package com.sflpro.identity.core.services.auth;

/**
 * Company: SFL LLC
 * Created on 8/31/2018
 *
 * @author Taron Petrosyan
 */
public class AuthenticationAttemptLimitReachedException extends AuthenticationServiceException {
    private static final String MESSAGE = "Authentication attempts limit reached";

    public AuthenticationAttemptLimitReachedException() {
        super(MESSAGE);
    }
}
