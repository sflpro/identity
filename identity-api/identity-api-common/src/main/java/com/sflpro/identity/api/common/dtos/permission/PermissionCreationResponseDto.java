package com.sflpro.identity.api.common.dtos.permission;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class PermissionCreationResponseDto extends AbstractApiResponse {

    private Long permissionId;

    public PermissionCreationResponseDto() {
        super();
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
