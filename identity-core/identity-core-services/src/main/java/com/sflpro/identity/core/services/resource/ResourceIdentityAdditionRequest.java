package com.sflpro.identity.core.services.resource;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 7/20/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceIdentityAdditionRequest {

    private Long resourceId;

    private Set<String> identityIds;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Set<String> getIdentityIds() {
        return identityIds;
    }

    public void setIdentityIds(Set<String> identityIds) {
        this.identityIds = identityIds;
    }
}
