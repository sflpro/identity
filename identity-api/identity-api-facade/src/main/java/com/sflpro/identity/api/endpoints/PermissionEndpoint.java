package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.permission.*;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Permission;
import com.sflpro.identity.core.services.permission.PermissionRequest;
import com.sflpro.identity.core.services.permission.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
@Component
@Path("/permissions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PermissionEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(PermissionEndpoint.class);

    @Autowired
    private BeanMapper mapper;

    @Autowired
    private PermissionService permissionService;

    @Operation(tags = {"permissions"}, summary = "Creates permission")
    @PUT
    @Transactional
    public PermissionCreationResponseDto create(@NotNull @Valid final PermissionCreationRequestDto creationRequestDto) {
        Assert.notNull(creationRequestDto, "creationRequestDto cannot be null");
        // Compose permission creation request
        PermissionRequest permissionRequest = mapper.map(creationRequestDto, PermissionRequest.class);
        // Create Permission
        Permission permission = permissionService.create(permissionRequest);
        logger.info("Created permission:'{}'.", permission.getId());
        return mapper.map(permission, PermissionCreationResponseDto.class);
    }

    @Operation(tags = {"permissions"}, summary = "Updates permission's details")
    @PUT
    @Path("/{permissionId}")
    @Transactional
    public PermissionUpdateResponseDto update(@NotNull @PathParam("permissionId") final Long permissionId,
                                              @NotNull @Valid final PermissionUpdateRequestDto updateRequestDto) {
        Assert.notNull(updateRequestDto, "updateRequestDto cannot be null");
        // Compose permission update
        PermissionRequest permissionRequest = mapper.map(updateRequestDto, PermissionRequest.class);
        permissionRequest.setId(permissionId);
        // Update Permission
        Permission permission = permissionService.update(permissionRequest);
        logger.info("Updated permission:'{}'.", permission.getId());
        return mapper.map(permission, PermissionUpdateResponseDto.class);
    }

    @Operation(tags = {"permissions"}, summary = "Returns permission's details")
    @GET
    @Path("/{permissionId}")
    @Transactional(readOnly = true)
    public PermissionDto get(@NotNull @PathParam("permissionId") final Long permissionId) {
        Assert.notNull(permissionId, "permissionId cannot be empty");
        // Get permission
        Permission permission = permissionService.get(permissionId);
        logger.info("Found permission:'{}'.", permission.getId());
        return mapper.map(permission, PermissionDto.class);
    }
}
