package com.sflpro.identity.core.services.resource;

import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Resource;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 14/02/2018
 *
 * @author Davit Harutyunyan
 */
public interface ResourceService {

    Resource create(final ResourceCreationRequest resourceRequest);

    Resource get(final long resourceId);

    Resource update(final ResourceUpdateRequest resourceRequest);

    Resource delete(final long resourceId);

    List<Resource> list(String identityId, String resourceType, String resourceIdentifier);

    List<Resource> get(final List<ResourceRequest> resourceRequests, final Identity identity);

}
