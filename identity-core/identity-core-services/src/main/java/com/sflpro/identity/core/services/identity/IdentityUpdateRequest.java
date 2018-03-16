package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.services.principal.PrincipalUpdateDetailsRequest;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 17/02/2018
 *
 * @author Davit Harutyunyan
 */
public class IdentityUpdateRequest {

    private String description;

    private String secret;

    private String newSecret;

    private String contactMethod;

    private List<PrincipalUpdateDetailsRequest> principals;

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

    public String getNewSecret() {
        return newSecret;
    }

    public void setNewSecret(String newSecret) {
        this.newSecret = newSecret;
    }

    public List<PrincipalUpdateDetailsRequest> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<PrincipalUpdateDetailsRequest> principals) {
        this.principals = principals;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }
}
