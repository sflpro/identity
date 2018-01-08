package com.sflpro.identity.api.common.dtos.auth.mechanism;

import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDetailsDto;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.PrincipalType;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;


/**
 * Company: SFL LLC
 * Created on 23/12/2017
 *
 * @author Davit Harutyunyan
 */
@ApiModel(value = "TokenDetails", parent = AuthenticationRequestDetailsDto.class)
public final class TokenAuthenticationRequestDetailsDto extends AuthenticationRequestDetailsDto {

    @NotNull
    private String tokenType;

    @NotNull
    private String token;

    public TokenAuthenticationRequestDetailsDto() {
        super(CredentialType.TOKEN);
    }

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
