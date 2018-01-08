package com.sflpro.identity.core.services.token;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenExistenceCheckRequest {

    private String tokenType;
    private String tokenValue;

    public TokenExistenceCheckRequest(String tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
