package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceCreationRequestDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceUpdateRequestDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.services.identity.IdentityService;
import com.sflpro.identity.core.services.resource.ResourceCreationRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import com.sflpro.identity.core.services.resource.ResourceUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 7/18/2018
 *
 * @author Taron Petrosyan
 */
@SwaggerDefinition(tags = {@Tag(name = "resources", description = "Resource CRUD operations")})
@Api(tags = {"resources"})
@Component
@Path("/resources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(ResourceEndpoint.class);
    @Autowired
    private BeanMapper mapper;
    
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private IdentityService identityService;
    
    @ApiOperation("Creates resource")
    @PUT
    @Transactional
    public ResourceDto create(@NotNull @Valid final ResourceCreationRequestDto creationRequestDto) {
        // Compose resource creation request
        ResourceCreationRequest resourceCreationRequest = mapper.map(creationRequestDto, ResourceCreationRequest.class);
        // Create resource
        Resource resource = resourceService.create(resourceCreationRequest);
        logger.info("Created resource:'{}'.", resource.getId());
        return mapper.map(resource, ResourceDto.class);
    }

    @ApiOperation("Returns resource's details")
    @GET
    @Path("/{resourceId}")
    @Transactional(readOnly = true)
    public ResourceDto get(@NotNull @PathParam("resourceId") final Long resourceId) {
        // Get resource
        Resource resource = resourceService.get(resourceId);
        logger.info("Found resource:'{}'.", resource.getId());
        return mapper.map(resource, ResourceDto.class);
    }

    @ApiOperation("Updates resource's details")
    @PUT
    @Path("/{resourceId}")
    @Transactional
    public ResourceDto update(@NotNull @PathParam("resourceId") final Long resourceId,
                                        @NotNull @Valid final ResourceUpdateRequestDto updateRequestDto) {
        // Compose resource update
        ResourceUpdateRequest resourceUpdateRequest = mapper.map(updateRequestDto, ResourceUpdateRequest.class);
        resourceUpdateRequest.setId(resourceId);
        // Update Resource
        Resource resource = resourceService.update(resourceUpdateRequest);
        logger.info("Updated resource:'{}'.", resource.getId());
        return mapper.map(resource, ResourceDto.class);
    }

    @ApiOperation("Deletes resource")
    @DELETE
    @Path("/{resourceId}")
    @Transactional
    public ApiResponseDto delete(@NotNull @PathParam("resourceId") final Long resourceId) {
        // Delete resource
        Resource resource = resourceService.delete(resourceId);
        logger.info("Deleted resource:'{}'.", resource.getId());
        return new ApiResponseDto();
    }

    @ApiOperation("Lists resources")
    @GET
    @Transactional(readOnly = true)
    public ApiGenericListResponse<ResourceDto> list(@QueryParam("type") final String type,
                                                    @QueryParam("identifier") final String identifier) {
        // list resources
        List<Resource> resources = resourceService.list(type, identifier);
        logger.info("Found {} resources", resources.size());
        List<ResourceDto> result = mapper.mapAsList(resources, ResourceDto.class);
        return new ApiGenericListResponse<>(result.size(), result);
    }

    @ApiOperation("Lists resource's identities")
    @GET
    @Path("/{resourceId}/identities")
    @Transactional(readOnly = true)
    public ApiGenericListResponse<IdentityDto> listIdentities(@NotNull @PathParam("resourceId") final Long resourceId) {
        // Get identities
        List<Identity> identities = identityService.list(resourceId);
        logger.info("Found {} identities for resource:'{}'.", identities.size(), resourceId);
        List<IdentityDto> result = mapper.mapAsList(identities, IdentityDto.class);
        return new ApiGenericListResponse<>(result.size(), result);
    }
}
