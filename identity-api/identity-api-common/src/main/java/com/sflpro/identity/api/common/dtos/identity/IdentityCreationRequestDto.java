package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.credential.CredentialCreationDto;
import com.sflpro.identity.core.datatypes.IdentityContactMethod;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityCreationRequestDto {

    @NotEmpty
    private String description;

    @NotEmpty
    private String secret;

    @NotNull
    private List<CredentialCreationDto> credentials;

    private IdentityContactMethod contactMethod;

    public IdentityCreationRequestDto() {
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

    public IdentityContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(IdentityContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    public List<CredentialCreationDto> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<CredentialCreationDto> credentials) {
        this.credentials = credentials;
    }
}
