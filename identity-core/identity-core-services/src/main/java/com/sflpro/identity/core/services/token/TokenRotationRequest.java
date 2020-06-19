package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 19/06/2020
 *
 * @author Davit Harutyunyan
 */
public class TokenRotationRequest {

    @NotNull
    private final TokenType tokenType;

    @NotNull
    private final String token;

    public TokenRotationRequest(@NotNull TokenType tokenType, @NotNull String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }
}
