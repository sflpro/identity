package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Credential;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface AuthenticatorRegistry {

    /**
     * Authenticator for auth request details
     *
     * @param authenticationRequestDetailsClazz auth request details class object
     * @param <T> credential
     * @param <E> credential identifier
     * @param <S> credential request details
     * @return Authenticator object
     */
    <T extends Credential, E extends CredentialIdentifier<T>, S extends AuthenticationRequestDetails<T, E>> Authenticator<T, E, S> getAuthenticator(Class<S> authenticationRequestDetailsClazz);

    /**
     * Gets credential store
     *
     * @param credentialIdentifierClazz credential identifier class object
     * @param <T> Credential
     * @param <E> Credential identifier
     * @return credential store
     */
    <T extends Credential, E extends CredentialIdentifier<T>> CredentialStore<T, E> getCredentialStore(Class<E> credentialIdentifierClazz);

    List<CredentialType> getSupportedCredentialTypes();
}
