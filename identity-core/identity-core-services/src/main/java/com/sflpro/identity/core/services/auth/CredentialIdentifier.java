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
public abstract class CredentialIdentifier<T extends Credential> {

    @NotNull
    private CredentialType credentialType;

    private final Class<CredentialIdentifier<T>> clazz;

    public CredentialIdentifier(@NotNull CredentialType credentialType) {
        this.credentialType = credentialType;
        this.clazz = (Class<CredentialIdentifier<T>>) getClass();
    }

    public CredentialType getCredentialType() {
        return credentialType;
    }

    public abstract Class<T> getCredentialClass();

    public final Class<CredentialIdentifier<T>> getGenericClass() {
        return clazz;
    }
}
