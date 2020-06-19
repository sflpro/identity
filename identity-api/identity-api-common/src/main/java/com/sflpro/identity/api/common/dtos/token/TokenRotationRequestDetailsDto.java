package com.sflpro.identity.api.common.dtos.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sflpro.identity.core.datatypes.TokenType;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 19/06/2020
 *
 * @author Davit Harutyunyan
 */
public class TokenRotationRequestDetailsDto {

    @NotEmpty
    private final TokenType tokenType;

    @NotEmpty
    private final String token;

    @JsonCreator
    public TokenRotationRequestDetailsDto(@JsonProperty("tokenType") final TokenType tokenType,
                                          @JsonProperty("token") final String token) {
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
