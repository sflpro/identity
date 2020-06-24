package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.services.resource.ResourceRequest;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenRequest {

    private TokenType tokenType;

    private Integer expiresInHours;
    
    private ResourceRequest resourceRequest;

    public TokenRequest(TokenType tokenType) {
        this.tokenType = tokenType;
        this.expiresInHours = null;
    }

    public TokenRequest(TokenType tokenType, Integer expiresInHours, final ResourceRequest resourceRequest) {
        this.tokenType = tokenType;
        this.expiresInHours = expiresInHours;
        this.resourceRequest = resourceRequest;
    }

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

    public ResourceRequest getResourceRequest() {
        return resourceRequest;
    }

    public void setResourceRequest(final ResourceRequest resourceRequest) {
        this.resourceRequest = resourceRequest;
    }
}
