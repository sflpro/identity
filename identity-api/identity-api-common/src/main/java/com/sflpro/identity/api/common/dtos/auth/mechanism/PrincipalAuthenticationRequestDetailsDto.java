package com.sflpro.identity.api.common.dtos.auth.mechanism;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDetailsDto;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.PrincipalType;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Company: SFL LLC
 * Created on 23/12/2017
 *
 * @author Davit Harutyunyan
 */
@ApiModel(value = "PrincipalDetails", parent = AuthenticationRequestDetailsDto.class)
public final class PrincipalAuthenticationRequestDetailsDto extends AuthenticationRequestDetailsDto {

    @NotNull
    private final PrincipalType principalType;

    @NotEmpty
    private final String principal;

    @NotEmpty
    private final String secret;

    @JsonCreator
    public PrincipalAuthenticationRequestDetailsDto(@JsonProperty("principalType") final PrincipalType type,
                                                    @JsonProperty("principal") final String principal,
                                                    @JsonProperty("secret") final String secret) {
        super(CredentialType.PRINCIPAL);
        this.principalType = type;
        this.principal = principal;
        this.secret = secret;
    }

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getSecret() {
        return secret;
    }
}
