package com.sflpro.identity.core.services.notification;

import java.util.Map;

/**
 * Company: SFL LLC
 * Created on 7/4/2019
 *
 * @author Taron Petrosyan
 */
public class SecretResetNotificationRequest {

    private String email;

    private String emailTemplateName;


    private Map<String, String> emailTemplateProperties;

    public SecretResetNotificationRequest(String email, String emailTemplateName, Map<String, String> emailTemplateProperties) {
        this.email = email;
        this.emailTemplateName = emailTemplateName;
        this.emailTemplateProperties = emailTemplateProperties;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
