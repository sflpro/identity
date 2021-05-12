package com.sflpro.identity.api.common.dtos.token;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;
import com.sflpro.identity.core.datatypes.TokenType;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "tokenType", visible = true, defaultImpl = TokenRequestDto.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value= AccessTokenRequestDto.class, name="ACCESS")
})
public class TokenRequestDto {

    private TokenType tokenType;

    private Integer expiresInHours;

    private Integer expiresInMinutes;

    private ResourceRequestDto roleResource;

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresInHours() {
        return expiresInHours;
    }

    public void setExpiresInHours(Integer expiresInHours) {
        this.expiresInHours = expiresInHours;
    }

    public Integer getExpiresInMinutes() {
        return expiresInMinutes;
    }

    public void setExpiresInMinutes(Integer expiresInMinutes) {
        this.expiresInMinutes = expiresInMinutes;
    }

    public ResourceRequestDto getRoleResource() {
        return roleResource;
    }

    public void setRoleResource(ResourceRequestDto roleResource) {
        this.roleResource = roleResource;
    }
}
