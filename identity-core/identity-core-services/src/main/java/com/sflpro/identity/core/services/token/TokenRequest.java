package com.sflpro.identity.core.services.token;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenRequest {

    private String tokenType;

    private Integer expiresInHours;

    public TokenRequest(String tokenType, Integer expiresInHours) {
        this.tokenType = tokenType;
        this.expiresInHours = expiresInHours;
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
}
