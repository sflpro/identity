package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.role.*;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Role;
import com.sflpro.identity.core.services.role.RoleRequest;
import com.sflpro.identity.core.services.role.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
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
@SwaggerDefinition(tags = {@Tag(name = "roles", description = "Role CRUD operations")})
@Api(tags = {"roles"})
@Component
@Path("/role")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(RoleEndpoint.class);

    @Autowired
    private BeanMapper mapper;

    @Autowired
    private RoleService roleService;

    @ApiOperation("Creates role")
    @PUT
    @Transactional
    public RoleDto create(@NotNull @Valid final RoleCreationRequestDto creationRequestDto) {
        Assert.notNull(creationRequestDto, "creationRequestDto cannot be null");
        // Compose role creation request
        RoleRequest roleRequest = mapper.map(creationRequestDto, RoleRequest.class);
        // Create Role
        Role role = roleService.create(roleRequest);
        logger.info("Created role:'{}'.", role.getId());
        return mapper.map(role, RoleDto.class);
    }

    @ApiOperation("Updates role's details")
    @PUT
    @Path("/{roleId}")
    @Transactional
    public RoleUpdateResponseDto update(@NotNull @PathParam("roleId") final Long roleId,
                                        @NotNull @Valid final RoleUpdateRequestDto updateRequestDto) {
        Assert.notNull(updateRequestDto, "updateRequestDto cannot be null");
        // Compose role update
        RoleRequest roleUpdateRequest = mapper.map(updateRequestDto, RoleRequest.class);
        // Update Role
        Role role = roleService.update(roleUpdateRequest);
        logger.info("Updated role:'{}'.", role.getId());
        return mapper.map(role, RoleUpdateResponseDto.class);
    }

    @ApiOperation("Returns role's details")
    @GET
    @Path("/{roleId}")
    @Transactional(readOnly = true)
    public RoleDto get(@NotNull @PathParam("roleId") final Long roleId) {
        Assert.notNull(roleId, "roleId cannot be empty");
        // Get role
        Role role = roleService.get(roleId);
        logger.info("Found role:'{}'.", role.getId());
        return mapper.map(role, RoleDto.class);
    }
}
