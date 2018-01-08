package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.datatypes.AuthenticationStatus;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Token;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class AuthenticationResponse {

    private CredentialType credentialTypeUsed;

    private String identityId;

    private List<String> permissions;

    private List<Token> tokens;

    private AuthenticationStatus status;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(Credential credentialsUsed, Identity identity) {
        this.credentialTypeUsed = credentialsUsed.getType();
        this.identityId = identity.getId();
    }

    public CredentialType getCredentialTypeUsed() {
        return credentialTypeUsed;
    }

    public void setCredentialTypeUsed(CredentialType credentialTypeUsed) {
        this.credentialTypeUsed = credentialTypeUsed;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public AuthenticationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthenticationStatus status) {
        this.status = status;
    }
}
