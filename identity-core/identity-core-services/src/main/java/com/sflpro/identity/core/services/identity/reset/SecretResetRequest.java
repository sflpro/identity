package com.sflpro.identity.core.services.identity.reset;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class SecretResetRequest {
    private String token;
    private String secret;

    public SecretResetRequest(String token, String secret) {
        this.token = token;
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public String getSecret() {
        return secret;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setPassword(final String secret) {
        this.secret = secret;
    }
}
