package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Credential;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 11/30/17
 *
 * @author Yervand Aghababyan
 */
public interface Authenticator<T extends Credential, E extends CredentialIdentifier<T>, S extends AuthenticationRequestDetails<T, E>> {
    /**
     * Authenticates by credential
     *
     * @param credential credentials for authenticating
     * @param authenticationRequest details for authenticating
     * @return authenticated identity, permissions and tokens list
     * @throws AuthenticationServiceException returns authentication service exceptions, such as inactive identity, etc
     */
    AuthenticationResponse authenticate(@NotNull T credential, @NotNull S authenticationRequest) throws AuthenticationServiceException;

    /**
     * Returns credential types supported by current authenticator
     *
     * @return redential type
     */
    CredentialType getSupportedCredentialType();

    /**
     * Returns credential request details for authenticator
     *
     * @return class object
     */
    Class<S> getAuthenticationRequestDetailsType();
}
