package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.services.resource.ResourceRequest;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 7/23/2018
 *
 * @author Taron Petrosyan
 */
public class IdentityResourceUpdateRequest {

    private String identityId;

    private List<ResourceRequest> resourceRequests;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public List<ResourceRequest> getResourceRequests() {
        return resourceRequests;
    }

    public void setResourceRequests(List<ResourceRequest> resourceRequests) {
        this.resourceRequests = resourceRequests;
    }
}
