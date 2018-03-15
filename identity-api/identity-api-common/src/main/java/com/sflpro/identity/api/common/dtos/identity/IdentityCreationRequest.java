package com.sflpro.identity.api.common.dtos.identity;

import java.util.Optional;

public class IdentityCreationRequest {

    private  String description;

    private  String contactMethod;

    private Optional<String> status;

    private  String secret;

    private Optional<String> creatorId;

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

    public Optional<String> getStatus() {
        return status;
    }

    public void setStatus(Optional<String> status) {
        this.status = status;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Optional<String> getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Optional<String> creatorId) {
        this.creatorId = creatorId;
    }
}
