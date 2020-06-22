package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.IdentityResourceRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/22/20
 *
 * @author Manuk Gharslyan
 */
public interface IdentityResourceRoleRepository extends JpaRepository<IdentityResourceRole, Long> {

    Set<IdentityResourceRole> findAllByDeletedIsNullAndIdentityIdAndResourceId(final String identityId, final Long resourceId);
}