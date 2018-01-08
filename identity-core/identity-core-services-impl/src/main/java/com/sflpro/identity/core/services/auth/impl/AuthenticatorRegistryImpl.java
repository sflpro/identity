package com.sflpro.identity.core.services.auth.impl;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.services.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class AuthenticatorRegistryImpl implements AuthenticatorRegistry {

    private final Map<Class<? extends AuthenticationRequestDetails<?, ?>>, Authenticator<?, ?, ?>> authenticators = new HashMap<>();
    private final Map<Class<? extends CredentialIdentifier<?>>, CredentialStore<?, ?>> credentialStores = new HashMap<>();
    private final List<CredentialType> supportedCredentialTypes;

    @Autowired
    public AuthenticatorRegistryImpl(List<Authenticator<?, ?, ?>> authenticators, List<CredentialStore<?, ?>> credentialStores) {

        List<CredentialType> tmpSupportedCredentialTypes = new ArrayList<>();

        credentialStores.forEach(cs -> this.credentialStores.put(cs.getCredentialIdentifierType(), cs));
        authenticators.forEach(a -> {
            this.authenticators.put(a.getAuthenticationRequestDetailsType(), a);
            tmpSupportedCredentialTypes.add(a.getSupportedCredentialType());
        });

        supportedCredentialTypes = Collections.unmodifiableList(tmpSupportedCredentialTypes);
    }

    @Override
    public <T extends Credential, E extends CredentialIdentifier<T>, S extends AuthenticationRequestDetails<T, E>> Authenticator<T, E, S> getAuthenticator(Class<S> authenticationRequestDetailsClazz) {
        Authenticator<?, ?, ?> authenticator =  authenticators.get(authenticationRequestDetailsClazz);

        if(!authenticator.getAuthenticationRequestDetailsType().equals(authenticationRequestDetailsClazz) ) {
            throw new IllegalStateException(String.format("Unexpected authenticator:%s mapped for details:%s!", authenticator.getClass().getCanonicalName(), authenticationRequestDetailsClazz.getCanonicalName()));
        }

        return (Authenticator<T, E, S>) authenticator;
    }

    @Override
    public <T extends Credential, E extends CredentialIdentifier<T>> CredentialStore<T, E> getCredentialStore(Class<E> credentialIdentifierClazz) {
        CredentialStore<?, ?> credentialStore = credentialStores.get(credentialIdentifierClazz);

        if(!credentialStore.getCredentialIdentifierType().equals(credentialIdentifierClazz) ) {
            throw new IllegalStateException(String.format("Unexpected credentialStore:%s mapped for identifier:%s!", credentialStore.getClass().getCanonicalName(), credentialIdentifierClazz.getCanonicalName()));
        }

        return (CredentialStore<T, E>) credentialStore;
    }

    @Override
    public List<CredentialType> getSupportedCredentialTypes() {
        return new ArrayList<>(supportedCredentialTypes);
    }
}
