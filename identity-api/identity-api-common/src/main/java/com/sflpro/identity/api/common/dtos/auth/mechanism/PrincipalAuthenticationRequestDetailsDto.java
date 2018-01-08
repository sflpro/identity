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
@ApiModel(value = "PrincipalDetails", parent = AuthenticationRequestDetailsDto.class)
public final class PrincipalAuthenticationRequestDetailsDto extends AuthenticationRequestDetailsDto {

    private PrincipalType principalType;

    @NotNull
    private String principal;

    @NotNull
    private String secret;

    public PrincipalAuthenticationRequestDetailsDto() {
        super(CredentialType.PRINCIPAL);
    }

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(PrincipalType principalType) {
        this.principalType = principalType;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
