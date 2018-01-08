package com.sflpro.identity.core.services.permission;

import com.sflpro.identity.core.db.entities.Permission;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface PermissionService {

    /**
     * Creates new permission
     *
     * @param permissionRequest permission creation request model
     * @return id and details of the created permission
     */
    Permission create(final PermissionRequest permissionRequest);

    /**
     * Updates permission
     *
     * @param permissionRequest permission request model
     * @return id and details of the updated permission
     */
    Permission update(final PermissionRequest permissionRequest);

    /**
     * Gets permission
     *
     * @param permissionId permission id
     * @return details of the permission
     */
    Permission get(@NotNull final Long permissionId);
}
