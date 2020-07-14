package com.sflpro.identity.core.services.identity.resource.role;

/**
 * Company: SFL LLC
 * Created on 24/06/2020
 *
 * @author Davit Harutyunyan
 */
public class IdentityResourceRoleCreationRequest {

    private final String identityId;
    private final Long roleId;
    private final Long resourceId;

    public IdentityResourceRoleCreationRequest(String identityId, Long roleId) {
        this.identityId = identityId;
        this.roleId = roleId;
        this.resourceId = null;
    }

    public IdentityResourceRoleCreationRequest(String identityId, Long roleId, Long resourceId) {
        this.identityId = identityId;
        this.roleId = roleId;
        this.resourceId = resourceId;
    }

    public String getIdentityId() {
        return identityId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }
}
