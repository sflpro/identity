package com.sflpro.identity.core.services.principal;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 19/02/2018
 *
 * @author Davit Harutyunyan
 */
public class PrincipalUpdateRequest {

    private String secret;

    private List<PrincipalUpdateDetailsRequest> updateDetailsRequests;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<PrincipalUpdateDetailsRequest> getUpdateDetailsRequests() {
        return updateDetailsRequests;
    }

    public void setUpdateDetailsRequests(List<PrincipalUpdateDetailsRequest> updateDetailsRequests) {
        this.updateDetailsRequests = updateDetailsRequests;
    }
}
