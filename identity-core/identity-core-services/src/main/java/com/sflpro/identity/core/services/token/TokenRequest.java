package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenRequest {

    private TokenType tokenType;

    private Integer expiresInHours;

    public TokenRequest(TokenType tokenType, Integer expiresInHours) {
        this.tokenType = tokenType;
        this.expiresInHours = expiresInHours;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresInHours() {
        return expiresInHours;
    }

    public void setExpiresInHours(Integer expiresInHours) {
        this.expiresInHours = expiresInHours;
    }
}
