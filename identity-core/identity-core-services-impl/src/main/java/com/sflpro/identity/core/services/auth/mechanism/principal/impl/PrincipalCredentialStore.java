package com.sflpro.identity.core.services.auth.mechanism.principal.impl;

import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.services.auth.CredentialStore;
import com.sflpro.identity.core.services.auth.mechanism.principal.PrincipalCredentialIdentifier;
import com.sflpro.identity.core.services.principal.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Company: SFL LLC
 * Created on 01/12/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class PrincipalCredentialStore implements CredentialStore<Principal, PrincipalCredentialIdentifier> {

    private final PrincipalService principalService;

    @Autowired
    public PrincipalCredentialStore(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @Override
    public Principal get(final PrincipalCredentialIdentifier identifier) {
        return principalService.get(identifier.getPrincipalType(), identifier.getPrincipal());
    }

    @Override
    public Class<PrincipalCredentialIdentifier> getCredentialIdentifierType() {
        return PrincipalCredentialIdentifier.class;
    }
}
