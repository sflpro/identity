package com.sflpro.identity.api.common.dtos.identity.reset;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class SecretResetRequestDto {

    @NotEmpty
    private String token;

    @NotEmpty
    private String secret;

    public SecretResetRequestDto() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
