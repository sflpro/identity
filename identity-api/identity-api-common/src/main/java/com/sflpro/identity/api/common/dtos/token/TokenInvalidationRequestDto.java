package com.sflpro.identity.api.common.dtos.token;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 16/01/2018
 *
 * @author Davit Harutyunyan
 */
public class TokenInvalidationRequestDto {

    @NotNull
    private String tokenType;

    @NotEmpty
    private String token;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
