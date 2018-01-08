package com.sflpro.identity.api.common.dtos.role;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class RoleCreationResponseDto {

    private Long roleId;

    public RoleCreationResponseDto() {
        super();
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
