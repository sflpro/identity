package com.sflpro.identity.core.services.identity.reset;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class ValidateSecretResetTokenRequest {

    private String tokenType;

    private String tokenValue;

    public ValidateSecretResetTokenRequest(String tokenType, String tokenValue) {
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
