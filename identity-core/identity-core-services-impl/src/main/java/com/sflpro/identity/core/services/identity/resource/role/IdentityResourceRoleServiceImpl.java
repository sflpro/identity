package com.sflpro.identity.core.services.identity.resource.role;

import com.sflpro.identity.core.db.entities.IdentityResourceRole;
import com.sflpro.identity.core.db.repositories.IdentityResourceRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IdentityResourceRoleRepository identityResourceRoleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityResourceRole create(final IdentityResourceRoleCreationRequest creationRequest) {
        Assert.notNull(creationRequest, "creationRequest cannot be null");
        logger.trace("Creating identityResourceRole for request:{}...", creationRequest);
        final IdentityResourceRole identityResourceRole = new IdentityResourceRole();
        identityResourceRole.setIdentityId(creationRequest.getIdentityId());
        identityResourceRole.setRoleId(creationRequest.getRoleId());
        identityResourceRole.setResourceId(creationRequest.getResourceId());
        final var saved = identityResourceRoleRepository.save(identityResourceRole);
        em.flush();
        logger.debug("Done creating identityResourceRole for request:{}...", creationRequest);
        return saved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Set<IdentityResourceRole> getAllByIdentityIdAndResourceId(final String identityId, final Long resourceId) {
        Assert.hasText(identityId, "The identityId should not be null or empty");
        logger.trace("Getting the identity resource roles for identityId - {} and resourceId - {}", identityId, resourceId);
        final Set<IdentityResourceRole> response = identityResourceRoleRepository.findAllByDeletedIsNullAndIdentityIdAndResourceId(identityId, resourceId);
        logger.debug("Done getting the identity resource roles for identityId - {} and resourceId - {}", identityId, resourceId);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void deleteByIdentityAndResource(final String identityId, final Long resourceId) {
        Assert.hasText(identityId, "The identityId should not be null or empty");
        logger.trace("Getting the identity resource roles for identityId - {} and resourceId - {}", identityId, resourceId);
        final Set<IdentityResourceRole> response = identityResourceRoleRepository.findAllByDeletedIsNullAndIdentityIdAndResourceId(identityId, resourceId);
        identityResourceRoleRepository.deleteAll(response);
        em.flush();
        logger.debug("Done getting the identity resource roles for identityId - {} and resourceId - {}", identityId, resourceId);
    }

    @Transactional
    @Override
    public Set<IdentityResourceRole> getAllByIdentityId(final String identityId) {
        return identityResourceRoleRepository.findAllByIdentityIdAndDeletedIsNull(identityId);
    }
}