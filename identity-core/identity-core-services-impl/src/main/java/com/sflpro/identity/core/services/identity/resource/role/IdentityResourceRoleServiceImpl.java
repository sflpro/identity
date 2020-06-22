package com.sflpro.identity.core.services.identity.resource.role;

import com.sflpro.identity.core.db.entities.IdentityResourceRole;
import com.sflpro.identity.core.db.repositories.IdentityResourceRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/22/20
 *
 * @author Manuk Gharslyan
 */
@Service
public class IdentityResourceRoleServiceImpl implements IdentityResourceRoleService {
    private static final Logger logger = LoggerFactory.getLogger(IdentityResourceRoleServiceImpl.class);

    @Autowired
    private IdentityResourceRoleRepository identityResourceRoleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Set<IdentityResourceRole> getAllByIdentityIdAndResourceId(final String identityId, final Long resourceId) {
        Assert.hasText(identityId, "The identityId should not be null or empty");
        Assert.notNull(resourceId, "The resourceId should not be null");
        logger.trace("Getting the identity resource roles for identityId - {} and resourceId - {}", identityId, resourceId);
        final Set<IdentityResourceRole> response = identityResourceRoleRepository.findAllByDeletedIsNullAndIdentityIdAndResourceId(identityId, resourceId);
        logger.debug("Done getting the identity resource roles for identityId - {} and resourceId - {}", identityId, resourceId);
        return response;
    }
}