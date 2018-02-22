package com.sflpro.identity.api.common.dtos.auth.mechanism;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDetailsDto;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.TokenType;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 23/12/2017
 *
 * @author Davit Harutyunyan
 */
@ApiModel(value = "TokenDetails", parent = AuthenticationRequestDetailsDto.class)
public final class TokenAuthenticationRequestDetailsDto extends AuthenticationRequestDetailsDto {

    @NotEmpty
    private final TokenType tokenType;

    @NotEmpty
    private final String token;

    @JsonCreator
    public TokenAuthenticationRequestDetailsDto(@JsonProperty("tokenType") final TokenType tokenType,
                                                @JsonProperty("token") final String token) {
        super(CredentialType.TOKEN);
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
