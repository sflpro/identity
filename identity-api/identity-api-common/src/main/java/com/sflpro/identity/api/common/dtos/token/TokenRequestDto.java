package com.sflpro.identity.api.common.dtos.token;

import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;
import com.sflpro.identity.core.datatypes.TokenType;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenRequestDto {

    private TokenType tokenType;

    private Integer expiresInHours;

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

    public ResourceRequestDto getRoleResource() {
        return roleResource;
    }

    public void setRoleResource(ResourceRequestDto roleResource) {
        this.roleResource = roleResource;
    }
}
