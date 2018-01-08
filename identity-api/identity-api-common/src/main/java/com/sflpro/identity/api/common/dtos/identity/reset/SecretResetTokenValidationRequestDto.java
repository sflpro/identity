package com.sflpro.identity.api.common.dtos.identity.reset;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class SecretResetTokenValidationRequestDto {

    @NotEmpty
    private String tokenType;

    @NotEmpty
    private String token;

    public SecretResetTokenValidationRequestDto() {
        super();
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
