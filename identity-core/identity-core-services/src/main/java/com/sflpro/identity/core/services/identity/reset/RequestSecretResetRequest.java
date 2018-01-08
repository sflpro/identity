package com.sflpro.identity.core.services.identity.reset;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class RequestSecretResetRequest {

    @NotNull
    private String credentialId;

    private String tokenType;

    private Integer expiresIn;

    public RequestSecretResetRequest(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
