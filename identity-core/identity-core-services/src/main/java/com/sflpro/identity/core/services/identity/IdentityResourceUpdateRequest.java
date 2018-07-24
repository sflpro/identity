package com.sflpro.identity.core.services.identity;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 7/23/2018
 *
 * @author Taron Petrosyan
 */
public class IdentityResourceUpdateRequest {

    private String identityId;

    private List<Long> resourceIds;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public List<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
