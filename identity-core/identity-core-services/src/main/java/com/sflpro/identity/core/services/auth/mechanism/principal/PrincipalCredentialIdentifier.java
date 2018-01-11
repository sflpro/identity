package com.sflpro.identity.core.services.auth.mechanism.principal;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.services.auth.CredentialIdentifier;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 11/30/17
 *
 * @author Yervand Aghababyan
 */
public final class PrincipalCredentialIdentifier extends CredentialIdentifier<Principal> {

    @NotNull
    private final PrincipalType principalType;

    @NotNull
    private final String principal;

    public PrincipalCredentialIdentifier(PrincipalType principalType, @NotNull String principal) {
        super(CredentialType.PRINCIPAL);
        this.principalType = principalType;
        this.principal = principal;
    }

    public PrincipalCredentialIdentifier(@NotNull String principal) {
        super(CredentialType.PRINCIPAL);
        this.principalType = null;
        this.principal = principal;
    }

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public String getPrincipal() {
        return principal;
    }

    @Override
    public Class<Principal> getCredentialClass() {
        return Principal.class;
    }
}
