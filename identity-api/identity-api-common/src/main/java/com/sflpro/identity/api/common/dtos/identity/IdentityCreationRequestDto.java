package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;

import javax.validation.constraints.NotEmpty;

public class IdentityCreationRequestDto {

    @NotEmpty
    private  String description;

    @NotEmpty
    private  String contactMethod;

    private  String secret;

    private String status;

    private String creatorId;
    
    private ResourceRequestDto resourceRequestDto;

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

    public ResourceRequestDto getResourceRequestDto() {
        return resourceRequestDto;
    }

    public void setResourceRequestDto(final ResourceRequestDto resourceRequestDto) {
        this.resourceRequestDto = resourceRequestDto;
    }
}
