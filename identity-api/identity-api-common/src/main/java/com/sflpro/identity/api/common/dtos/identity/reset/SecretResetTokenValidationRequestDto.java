package com.sflpro.identity.api.common.dtos.identity.reset;

import com.sflpro.identity.core.datatypes.TokenType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class SecretResetTokenValidationRequestDto {

    @NotNull
    private TokenType tokenType;

    @NotEmpty
    private String token;

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
