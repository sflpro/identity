package com.sflpro.identity.api.common.dtos.role;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;
import com.sflpro.identity.api.common.dtos.permission.PermissionDto;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class RoleDto extends AbstractApiResponse {

    private Long id;

    private String name;

    private List<PermissionDto> permissions;

    public RoleDto() {
        super();
    }

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

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }
}
