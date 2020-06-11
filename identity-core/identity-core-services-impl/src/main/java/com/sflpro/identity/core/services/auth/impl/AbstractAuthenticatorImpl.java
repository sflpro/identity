package com.sflpro.identity.core.services.auth.impl;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.services.auth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public abstract class AbstractAuthenticatorImpl<T extends Credential, E extends CredentialIdentifier<T>, S extends AuthenticationRequestDetails<T, E>> implements Authenticator<T, E, S> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthenticatorImpl.class);

    private final CredentialType supportedCredentialType;

    public AbstractAuthenticatorImpl(CredentialType supportedCredentialType) {
        this.supportedCredentialType = supportedCredentialType;
    }

    public final AuthenticationResponse authenticate(@NotNull T credential, @NotNull S authenticationRequestDetails) throws AuthenticationServiceException {
        if (supportedCredentialType != authenticationRequestDetails.getCredentialType()) {
            logger.debug("Unsupported authenticationRequestDetails of type {} passed to to {}",
                    authenticationRequestDetails.getClass().getCanonicalName(),
                    getClass().getCanonicalName());
            throw new IllegalStateException(String.format("Unsupported authenticationRequestDetails of type %s passed to %s",
                    authenticationRequestDetails.getClass().getCanonicalName(),
                    getClass().getCanonicalName()));
        }

        return processAuthentication(credential, authenticationRequestDetails);
    }

    protected abstract AuthenticationResponse processAuthentication(@NotNull T credential, @NotNull S authenticationRequestDetails) throws AuthenticationServiceException;

    public CredentialType getSupportedCredentialType() {
        return supportedCredentialType;
    }
}
