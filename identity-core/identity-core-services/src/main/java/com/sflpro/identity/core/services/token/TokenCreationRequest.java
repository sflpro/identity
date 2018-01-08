package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.db.entities.Credential;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenCreationRequest {

    private String tokenType;
    private Integer expiresInHours;
    private Credential tokenIssuedBy;

    public TokenCreationRequest(String tokenType, Integer expiresInHours, Credential tokenIssuedBy) {
        this.tokenType = tokenType;
        this.expiresInHours = expiresInHours;
        this.tokenIssuedBy = tokenIssuedBy;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresInHours() {
        return expiresInHours;
    }

    public void setExpiresInHours(Integer expiresInHours) {
        this.expiresInHours = expiresInHours;
    }

    public Credential getTokenIssuedBy() {
        return tokenIssuedBy;
    }

    public void setTokenIssuedBy(Credential tokenIssuedBy) {
        this.tokenIssuedBy = tokenIssuedBy;
    }
}
