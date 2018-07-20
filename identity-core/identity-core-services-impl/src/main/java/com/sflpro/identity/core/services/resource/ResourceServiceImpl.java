package com.sflpro.identity.core.services.resource;

import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.IdentityResource;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.db.repositories.IdentityRepository;
import com.sflpro.identity.core.db.repositories.IdentityResourceRepository;
import com.sflpro.identity.core.db.repositories.ResourceRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 14/02/2018
 *
 * @author Davit Harutyunyan
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private IdentityResourceRepository identityResourceRepository;

    @Autowired
    private IdentityRepository identityRepository;

    @Override
    @Transactional
    public Resource create(ResourceCreationRequest resourceCreationRequest) {
        logger.debug("Trying to create new resource: '{}'", resourceCreationRequest);
        Assert.notNull(resourceCreationRequest, "resourceCreationRequest cannot be null");
        Assert.notNull(resourceCreationRequest.getIdentifier(), "resourceCreationRequest.identifier cannot be null");
        Assert.notNull(resourceCreationRequest.getType(), "resourceCreationRequest.type cannot be null");

        Resource resource = new Resource();
        resource.setIdentifier(resourceCreationRequest.getIdentifier());
        resource.setType(resourceCreationRequest.getType());
        Resource saved = resourceRepository.save(resource);
        logger.trace("Resource created:'{}'", saved);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Resource get(long resourceId) {
        logger.debug("Trying to get resource: '{}'", resourceId);
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with %s id does not exist", resourceId)));
        logger.trace("Resource found:'{}'", resource);
        return resource;
    }

    @Override
    @Transactional
    public Resource update(ResourceUpdateRequest resourceUpdateRequest) {
        logger.debug("Trying to update resource: '{}'", resourceUpdateRequest);
        Assert.notNull(resourceUpdateRequest, "resourceUpdateRequest cannot be null");
        Assert.notNull(resourceUpdateRequest.getId(), "resourceUpdateRequest.id cannot be null");

        Resource resource = get(resourceUpdateRequest.getId());
        resource.setType(resourceUpdateRequest.getType());
        resource.setIdentifier(resourceUpdateRequest.getIdentifier());
        Resource saved = resourceRepository.save(resource);
        logger.trace("Resource updated:'{}'", saved);
        return saved;
    }

    @Override
    @Transactional
    public Resource delete(long resourceId) {
        logger.debug("Trying to delete resource: '{}'", resourceId);
        Resource resource = get(resourceId);
        resource.setDeleted(LocalDateTime.now());

        Resource saved = resourceRepository.save(resource);
        logger.trace("Resource deleted:'{}'", saved);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> list(String type, String identifier) {
        logger.debug("Trying to to list resources with type: '{}' and identifier: {}.", type, identifier);
        List<Resource> resources = resourceRepository.search(type, identifier);
        logger.trace("Finished listing resources with '{}' type and '{}' identifier.", type, identifier);
        return resources;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> search(String identityId, String resourceType, String resourceIdentifier) {
        logger.debug("Trying to to list resources for identity '{}'.", identityId);
        List<Resource> resources = identityResourceRepository.search(identityId, resourceType, resourceIdentifier);
        logger.trace("Finished listing resources with '{}' resource type and '{}' identifier for identity '{}'.", resourceType, resourceIdentifier, identityId);
        return resources;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> get(List<ResourceRequest> resourceRequests, Identity identity) {
        logger.debug("Attempting get resources identity {'{}'}.", identity);
        List<Resource> resources = new ArrayList<>();
        resourceRequests.forEach(r -> resources.addAll(identityResourceRepository.search(identity.getId(), r.getType(), r.getIdentifier())));
        logger.trace("Complete getting resources for identity {}.", identity);
        return resources;
    }

    @Override
    @Transactional
    public void addIdentities(ResourceIdentityAdditionRequest resourceIdentityAdditionRequest) {
        logger.debug("Trying to add identities to resource: '{}'", resourceIdentityAdditionRequest);
        Assert.notNull(resourceIdentityAdditionRequest, "resourceRequest cannot be null");
        Assert.notNull(resourceIdentityAdditionRequest.getResourceId(), "resourceRequest.id cannot be null");
        Assert.notEmpty(resourceIdentityAdditionRequest.getIdentityIds(), "resourceRequest.identityIds cannot be empty");
        Resource resource = get(resourceIdentityAdditionRequest.getResourceId());
        List<Identity> identities = identityRepository.findAllById(resourceIdentityAdditionRequest.getIdentityIds());
        List<Identity> missingIdentities = identities.stream()
                .filter(i -> !identityResourceRepository.existsByIdentityAndResource(i, resource))
                .collect(Collectors.toList());
        List<IdentityResource> identityResources = missingIdentities.stream()
                .map(i -> {
                    IdentityResource identityResource = new IdentityResource();
                    identityResource.setIdentity(i);
                    identityResource.setResource(resource);
                    return identityResource;
                })
                .collect(Collectors.toList());

        identityResourceRepository.saveAll(identityResources);
        logger.trace("Added {} identities to resource: '{}'", identityResources.size(), resource);
    }

    @Override
    @Transactional
    public void removeIdentity(final long resourceId, final String identityId) {
        logger.debug("Trying to remove identity '{}' from resource: '{}'", identityId, resourceId);
        Assert.notNull(identityId, "identityId cannot be null");
        Resource resource = get(resourceId);
        Identity identity = identityRepository.findByDeletedIsNullAndId(identityId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Identity with %s id not found", identityId)));
        IdentityResource identityResources = identityResourceRepository.findByIdentityAndResource(identity, resource);

        identityResourceRepository.delete(identityResources);
        logger.trace("Removed identity: '{}' from resource: '{}'", identity, resource);
    }
}
