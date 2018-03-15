package com.sflpro.identity.api.common.dtos.identity;

import javax.validation.constraints.NotEmpty;

public class IdentityCreationRequestDto {

    @NotEmpty
    private  String description;

    @NotEmpty
    private  String contactMethod;

    private String status;

    @NotEmpty
    private  String secret;

    private String creatorId;

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
}
