package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.services.resource.ResourceRequest;

/**
 * Company: SFL LLC
 * Created on 24/06/2020
 *
 * @author Davit Harutyunyan
 */
public class RoleAdditionRequest {

    private String name;

    private ResourceRequest resource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceRequest getResource() {
        return resource;
    }

    public void setResource(ResourceRequest resource) {
        this.resource = resource;
    }
}
