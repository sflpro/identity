package com.sflpro.identity.api.common.dtos.auth;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sflpro.identity.api.common.dtos.auth.mechanism.PrincipalAuthenticationRequestDetailsDto;
import com.sflpro.identity.api.common.dtos.auth.mechanism.TokenAuthenticationRequestDetailsDto;
import com.sflpro.identity.core.datatypes.CredentialType;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "credentialType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value=PrincipalAuthenticationRequestDetailsDto.class, name="PRINCIPAL"),
        @JsonSubTypes.Type(value=TokenAuthenticationRequestDetailsDto.class, name="TOKEN")
})
public abstract class AuthenticationRequestDetailsDto {

    @NotNull
    private final CredentialType credentialType;

    public AuthenticationRequestDetailsDto(@NotNull CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    public CredentialType getCredentialType() {
        return credentialType;
    }
}
