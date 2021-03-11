package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.resource.ResourceDto;

/**
 * Company: SFL LLC
 * Created on 09/03/2021
 *
 * @author Norik Aslanyan
 */
public class IdentityResourceRoleDto {

    private String roleName;

    private ResourceDto resource;

    public IdentityResourceRoleDto() {
    }

    public IdentityResourceRoleDto(String roleName, ResourceDto resource) {
        this.roleName = roleName;
        this.resource = resource;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ResourceDto getResource() {
        return resource;
    }

    public void setResource(ResourceDto resource) {
        this.resource = resource;
    }
}
