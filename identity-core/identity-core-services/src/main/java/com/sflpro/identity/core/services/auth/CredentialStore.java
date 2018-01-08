package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.db.entities.Credential;

/**
 * Company: SFL LLC
 * Created on 11/30/17
 *
 * @author Yervand Aghababyan
 */
public interface CredentialStore<T extends Credential, E extends CredentialIdentifier<T>> {
    /**
     * Gets Credential using identifier
     *
     * @param identifier credential identifier
     * @return Credential
     */
    T get(E identifier);

    /**
     * Gets credential identifier class
     *
     * @return class object
     */
    Class<E> getCredentialIdentifierType();
}
