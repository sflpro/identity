package com.sflpro.identity.core.services.credential;

import com.sflpro.identity.core.datatypes.CredentialType;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public class CredentialCreation {

    private CredentialType credentialType;

    private String details;

    public CredentialCreation() {
        super();
    }

    public CredentialType getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
