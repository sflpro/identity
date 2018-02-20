package com.sflpro.identity.core.services.resource;

import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.db.repositories.IdentityResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private IdentityResourceRepository identityResourceRepository;

    @Override
    public List<Resource> get(List<ResourceRequest> resourceRequests, Identity identity) {
        logger.debug("Attempting get resources identity {'{}'}.", identity);
        List<Resource> resources = new ArrayList<>();
        resourceRequests.forEach(r -> resources.addAll(identityResourceRepository.search(identity.getId(), r.getType(), r.getIdentifier())));
        logger.trace("Complete getting resources for identity {}.", identity);
        return resources;
    }
}
