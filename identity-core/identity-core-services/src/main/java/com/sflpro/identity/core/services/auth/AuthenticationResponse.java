package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.datatypes.AuthenticationStatus;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.*;

import java.util.List;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class AuthenticationResponse {

    private CredentialType credentialTypeUsed;

    private Identity identity;

    private Set<Principal> principals;

    private List<Token> tokens;

    private List<Resource> resources;

    private AuthenticationStatus status;

    public AuthenticationResponse(Credential credentialsUsed, Identity identity) {
        this.credentialTypeUsed = credentialsUsed.getType();
        this.identity = identity;
    }

    public CredentialType getCredentialTypeUsed() {
        return credentialTypeUsed;
    }

    public void setCredentialTypeUsed(CredentialType credentialTypeUsed) {
        this.credentialTypeUsed = credentialTypeUsed;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Set<Principal> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Set<Principal> principals) {
        this.principals = principals;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public AuthenticationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthenticationStatus status) {
        this.status = status;
    }
}
