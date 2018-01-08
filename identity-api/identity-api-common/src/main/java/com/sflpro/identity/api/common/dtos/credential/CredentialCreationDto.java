package com.sflpro.identity.api.common.dtos.credential;

import com.sflpro.identity.core.datatypes.CredentialType;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public abstract class CredentialCreationDto {

    @NotNull
    private CredentialType credentialType;

    private String details;

    public CredentialCreationDto() {
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
