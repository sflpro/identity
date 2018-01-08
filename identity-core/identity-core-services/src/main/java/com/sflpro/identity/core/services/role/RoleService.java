package com.sflpro.identity.core.services.role;

import com.sflpro.identity.core.db.entities.Role;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface RoleService {
    /**
     * Creates new Role
     *
     * @param roleRequest permission creation request model
     * @return id and details of the created permission
     */
    Role create(final RoleRequest roleRequest);

    /**
     * Updates permission
     *
     * @param roleUpdateRequest permission request model
     * @return id and details of the updated permission
     */
    Role update(final RoleRequest roleUpdateRequest);

    /**
     * Gets permission
     *
     * @param roleId permission id
     * @return details of the permission
     */
    Role get(final Long roleId);
}
