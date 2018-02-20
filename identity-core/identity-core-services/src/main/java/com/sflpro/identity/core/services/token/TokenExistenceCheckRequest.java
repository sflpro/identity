package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenExistenceCheckRequest {

    private TokenType tokenType;
    private String tokenValue;

    public TokenExistenceCheckRequest(TokenType tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
