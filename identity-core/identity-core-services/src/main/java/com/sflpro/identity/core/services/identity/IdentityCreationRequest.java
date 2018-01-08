package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.services.credential.CredentialCreation;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityCreationRequest {

    private String description;

    private String secret;

    private List<CredentialCreation> credentials;

    public IdentityCreationRequest() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<CredentialCreation> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<CredentialCreation> credentials) {
        this.credentials = credentials;
    }
}
