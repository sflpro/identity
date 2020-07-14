package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.services.token.TokenRequest;

import java.util.Set;

public class IdentityCreationRequest {

    private  String description;

    private  String contactMethod;

    private String status;

    private  String secret;

    private String creatorId;

    private Set<RoleAdditionRequest> roles;

    private Set<TokenRequest> tokenRequests;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Set<RoleAdditionRequest> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleAdditionRequest> roles) {
        this.roles = roles;
    }

    public Set<TokenRequest> getTokenRequests() {
        return tokenRequests;
    }

    public void setTokenRequests(Set<TokenRequest> tokenRequests) {
        this.tokenRequests = tokenRequests;
    }
}
