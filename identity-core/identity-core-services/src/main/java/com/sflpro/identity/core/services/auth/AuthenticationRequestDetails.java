package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Credential;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public abstract class AuthenticationRequestDetails<T extends Credential, E extends CredentialIdentifier<T>> {

    @NotNull
    private final CredentialType credentialType;

    @NotNull
    private final Class<AuthenticationRequestDetails<T, E>> clazz;

    public AuthenticationRequestDetails(@NotNull CredentialType credentialType) {
        this.credentialType = credentialType;

        this.clazz = (Class<AuthenticationRequestDetails<T, E>>) getClass();
    }

    public abstract E getCredentialIdentifier();

    public CredentialType getCredentialType() {
        return credentialType;
    }

    public Class<AuthenticationRequestDetails<T, E>> getGenericClass() {
        return clazz;
    }
}
