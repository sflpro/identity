package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;

/**
 * Company: SFL LLC
 * Created on 24/06/2020
 *
 * @author Davit Harutyunyan
 */
public class RoleAdditionRequestDto {

    private String name;

    private ResourceRequestDto resource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceRequestDto getResource() {
        return resource;
    }

    public void setResource(ResourceRequestDto resource) {
        this.resource = resource;
    }
}
