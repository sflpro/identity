package com.sflpro.identity.api.common.dtos.auth;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;
import com.sflpro.identity.api.common.dtos.identity.IdentityDto;
import com.sflpro.identity.api.common.dtos.token.TokenDto;
import com.sflpro.identity.core.datatypes.AuthenticationStatus;
import com.sflpro.identity.core.datatypes.CredentialType;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class AuthenticationResponseDto extends AbstractApiResponse {

    private CredentialType credentialTypeUsed;

    private String identityId;

    private List<String> permissions;

    private List<TokenDto> tokens;

    private AuthenticationStatus status;

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

    public List<TokenDto> getTokens() {
        return tokens;
    }

    public void setTokens(List<TokenDto> tokens) {
        this.tokens = tokens;
    }

    public AuthenticationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthenticationStatus status) {
        this.status = status;
    }
}
