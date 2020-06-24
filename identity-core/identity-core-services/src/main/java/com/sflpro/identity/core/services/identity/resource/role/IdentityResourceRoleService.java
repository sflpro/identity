package com.sflpro.identity.core.services.identity.resource.role;

import com.sflpro.identity.core.db.entities.IdentityResourceRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/22/20
 *
 * @author Manuk Gharslyan
 */
public interface IdentityResourceRoleService {

    /**
     * Creates IdentityResourceRole for provided request
     *
     * @param
     * @return
     */
    @Transactional
    IdentityResourceRole create(IdentityResourceRoleCreationRequest creationRequest);

    /**
     * Get set of identity role resources by identity id and resource id
     *
     * @param identityId
     * @param resourceId
     * @return
     */
    Set<IdentityResourceRole> getAllByIdentityIdAndResourceId(final String identityId, final Long resourceId);
}