package com.sflpro.identity.core.services.identity.reset;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class SecretResetRequest {

    private String tokenType;
    private String token;
    private String password;
    private String details;

    public SecretResetRequest(String tokenType, String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
