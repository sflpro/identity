package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationExceptionDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityCreationRequest;
import com.sflpro.identity.api.common.dtos.identity.IdentityCreationRequestDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityUpdateRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.RequestSecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetRequestDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.identity.IdentityService;
import com.sflpro.identity.core.services.identity.IdentityUpdateRequest;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
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
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
@SwaggerDefinition(tags = {@Tag(name = "identities", description = "Identity CRUD operations")})
@Api(tags = {"identities"})
@Component
@Path("/identities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IdentityEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(IdentityEndpoint.class);

    @Autowired
    private BeanMapper mapper;

    @Autowired
    private IdentityService identityService;

    @ApiOperation("Returns identity's details")
    @GET
    @Path("/{identityId}")
    @Transactional(readOnly = true)
    public IdentityDto get(@NotNull @PathParam("identityId") final String identityId) {
        Identity identity = identityService.get(identityId);
        logger.info("Got identity:'{}'.", identity.getId());
        return mapper.map(identity, IdentityDto.class);
    }




    @ApiOperation("Delete identity")
    @DELETE
    @Path("/{identityId}")
    @Deprecated
    @Transactional
    public IdentityDto delete(@NotNull @PathParam("identityId") final String identityId) {
        return new IdentityDto();
    }

    @ApiOperation("Request for secret reset")
    @PUT
    @Path("/secret-reset/request-token")
    @Transactional
    public ApiResponseDto requestSecretReset(@Valid RequestSecretResetRequestDto requestDto) {
        RequestSecretResetRequest request = mapper.map(requestDto, RequestSecretResetRequest.class);
        identityService.requestSecretReset(request);
        logger.info("Reset password request completed for user:'{}'.", requestDto.getEmail());
        return new ApiResponseDto();
    }

    @ApiOperation("Set new secret")
    @PUT
    @Path("/secret-reset/secret")
    @Transactional
    public ApiResponseDto secretReset(@Valid SecretResetRequestDto requestDto) {
        SecretResetRequest request = mapper.map(requestDto, SecretResetRequest.class);
        identityService.secretReset(request);
        logger.info("Reset secret was done by token:'{}'.", requestDto.getToken());
        return new ApiResponseDto();
    }

    @ApiOperation("Creates or Updates identity's details")
    @PUT
    @Path("/{identityId}")
    @Transactional
    public IdentityDto createIdentity(
            @PathVariable Optional<String> identityId,
            @NotNull final IdentityCreationRequestDto request) {
        Assert.notNull(request, "request cannot be null");
        logger.debug("Creating identity id {} with data :{}...", identityId, request);
        final IdentityCreationRequest creationRequest = mapper.map(request, IdentityCreationRequest.class);
        final Identity identity = identityService.add(creationRequest);
        logger.info("Done creating identity with data :{}....", creationRequest);
        return mapper.map(identity, IdentityDto.class);
    }
}
