package com.sflpro.identity.core.services.resource;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 7/20/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceIdentityAdditionRequest {

    private Long resourceId;

    private List<String> identityIds;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public List<String> getIdentityIds() {
        return identityIds;
    }

    public void setIdentityIds(List<String> identityIds) {
        this.identityIds = identityIds;
    }
}
