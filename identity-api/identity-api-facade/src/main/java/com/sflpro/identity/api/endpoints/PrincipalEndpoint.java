package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.auth.AuthenticationExceptionDto;
import com.sflpro.identity.api.common.dtos.principal.PrincipalDto;
import com.sflpro.identity.api.common.dtos.principal.PrincipalUpdateRequestDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.identity.core.services.principal.PrincipalUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 19/02/2018
 *
 * @author Davit Harutyunyan
 */
@SwaggerDefinition(tags = {@Tag(name = "principals", description = "Principal CRUD operations")})
@Api(tags = {"principals"})
@Component
@Path("/principals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrincipalEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalEndpoint.class);

    @Autowired
    private BeanMapper mapper;

    @Autowired
    private PrincipalService principalService;

    @ApiOperation("Updates principal's details")
    @PUT
    @Path("/{identityId}")
    public List<PrincipalDto> update(@NotNull @PathParam("identityId") final String identityId,
                                     @NotNull final PrincipalUpdateRequestDto updateRequestDto) {
        Assert.notNull(updateRequestDto, "updateRequestDto cannot be null");
        logger.debug("Updating principals with identity id: {}", identityId);
        try {
            PrincipalUpdateRequest updateRequests = mapper.map(updateRequestDto, PrincipalUpdateRequest.class);
            List<Principal> principals = principalService.update(identityId, updateRequests);
            logger.info("Done updating principals with identity id: {}", identityId);
            return mapper.mapAsList(principals, PrincipalDto.class);
        } catch (AuthenticationServiceException e) {
            logger.warn("Authentication failed for request:'{}'.", updateRequestDto);
            throw new AuthenticationExceptionDto(e.getMessage(), e);
        }
    }
}
