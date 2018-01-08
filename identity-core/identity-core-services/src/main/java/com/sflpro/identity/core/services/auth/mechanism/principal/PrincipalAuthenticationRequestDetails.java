package com.sflpro.identity.core.services.auth.mechanism.principal;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.services.auth.AuthenticationRequestDetails;

import javax.validation.constraints.NotNull;


/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public final class PrincipalAuthenticationRequestDetails extends AuthenticationRequestDetails<Principal, PrincipalCredentialIdentifier> {

    private final PrincipalType principalType;

    @NotNull
    private final String principal;

    @NotNull
    private final String secret;

    public PrincipalAuthenticationRequestDetails(@NotNull String principal, @NotNull String secret) {
        super(CredentialType.PRINCIPAL);
        this.principalType = null;
        this.principal = principal;
        this.secret = secret;
    }

    public PrincipalAuthenticationRequestDetails(PrincipalType principalType, @NotNull String principal, @NotNull String secret) {
        super(CredentialType.PRINCIPAL);
        this.principalType = principalType;
        this.principal = principal;
        this.secret = secret;
    }

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public PrincipalCredentialIdentifier getCredentialIdentifier() {
        return new PrincipalCredentialIdentifier(principalType, principal);
    }
}
