package com.sflpro.identity.api.common.dtos.identity.reset;

import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class RequestSecretResetRequestDto {

    @NotEmpty
    private String email;

    @NotNull
    private Integer expiresInHours;

    private String emailTemplateName;

    private String redirectUri;
    
    private ResourceRequestDto resourceRequest;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getExpiresInHours() {
        return expiresInHours;
    }

    public void setExpiresInHours(Integer expiresInHours) {
        this.expiresInHours = expiresInHours;
    }

    public String getEmailTemplateName() {
        return emailTemplateName;
    }

    public void setEmailTemplateName(String emailTemplateName) {
        this.emailTemplateName = emailTemplateName;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public ResourceRequestDto getResourceRequest() {
        return resourceRequest;
    }

    public void setResourceRequest(final ResourceRequestDto resourceRequest) {
        this.resourceRequest = resourceRequest;
    }
}