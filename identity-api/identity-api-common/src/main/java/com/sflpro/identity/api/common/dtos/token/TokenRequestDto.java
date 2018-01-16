package com.sflpro.identity.api.common.dtos.token;

import com.sflpro.identity.core.datatypes.TokenType;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenRequestDto {

    private TokenType tokenType;

    private Integer expiresInHours;

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
