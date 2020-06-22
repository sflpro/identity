package com.sflpro.identity.core.services.role;

import com.sflpro.identity.core.services.permission.PermissionRequest;
import com.sflpro.identity.core.services.resource.ResourceRequest;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 01/12/2017
 *
 * @author Davit Harutyunyan
 */
public class RoleRequest {

    private Long id;

    @NotEmpty
    private String name;

    private List<PermissionRequest> permission;

    private List<ResourceRequest> resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionRequest> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionRequest> permission) {
        this.permission = permission;
    }

    public List<ResourceRequest> getResource() {
        return resource;
    }

    public void setResource(final List<ResourceRequest> resource) {
        this.resource = resource;
    }
}
