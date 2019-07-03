package com.sflpro.identity.core.services.identity.reset;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class RequestSecretResetRequest {

    @NotNull
    private String email;

    @NotNull
    private Integer expiresInHours;

    @NotEmpty
    private String emailTemplateName;

    @NotEmpty
    private String redirectUri;

    @NotEmpty
    private Map<String, String> emailTemplateProperties;

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

    public Map<String, String> getEmailTemplateProperties() {
        return emailTemplateProperties;
    }

    public void setEmailTemplateProperties(Map<String, String> emailTemplateProperties) {
        this.emailTemplateProperties = emailTemplateProperties;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
