package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.ResourceNotFoundDto;
import com.sflpro.identity.api.common.dtos.identity.*;
import com.sflpro.identity.api.common.dtos.identity.reset.RequestSecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetRequestDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.identity.IdentityCheckPrincipalRequest;
import com.sflpro.identity.core.services.identity.IdentityCreationRequest;
import com.sflpro.identity.core.services.identity.IdentityService;
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

    @ApiOperation("Creates identity")
    @PUT
    @Transactional
    public IdentityCreationResponseDto create(@NotNull @Valid final IdentityCreationRequestDto identityCreationRequestDto) {
        // Compose authentication request
        IdentityCreationRequest identityCreationRequest = mapper.map(identityCreationRequestDto, IdentityCreationRequest.class);

        // Create Identity
        Identity identityCreationResponse = identityService.create(identityCreationRequest);
        logger.info("Created identity:'{}'.", identityCreationResponse.getId());
        IdentityCreationResponseDto identityCreationResponseDto = mapper.map(identityCreationResponse, IdentityCreationResponseDto.class);
        return identityCreationResponseDto;
    }

    @ApiOperation("Updates identity's details")
    @PUT
    @Path("/{identityId}")
    public IdentityUpdateResponseDto update(@NotNull @PathParam("identityId") final String identityId,
                                            final IdentityUpdateRequestDto updateRequestDto) {
        return new IdentityUpdateResponseDto();
    }

    @ApiOperation("Delete identity")
    @DELETE
    @Path("/{identityId}")
    public IdentityUpdateResponseDto delete(@NotNull @PathParam("identityId") final String identityId) {
        return new IdentityUpdateResponseDto();
    }

    @ApiOperation("Check principal's name availability by type and name")
    @POST
    @Path(("/check-principal"))
    @Transactional(readOnly = true)
    public IdentityDto checkPrincipalAvailability(@NotNull @Valid final IdentityCheckPrincipalRequestDto requestDto) {
        Assert.notNull(requestDto, "requestDto cannot be null");
        logger.debug("Checking identity name availability :{}...", requestDto);
        try {
            IdentityCheckPrincipalRequest checkMailRequest = mapper.map(requestDto, IdentityCheckPrincipalRequest.class);
            Identity identity = identityService.checkMailAvailability(checkMailRequest);
            logger.info("Done checking identity name availability :{}.", requestDto.getPrincipalName());
            return mapper.map(identity, IdentityDto.class);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundDto(String.format("Identity with type = %s, name = %s not found",
                    requestDto.getPrincipalType(), requestDto.getPrincipalName()), e);
        }
    }

    @ApiOperation("Request for secret reset")
    @PUT
    @Path("/secret-reset/request-token")
    public ApiResponseDto requestSecretReset(@Valid RequestSecretResetRequestDto requestDto) {
        RequestSecretResetRequest request = mapper.map(requestDto, RequestSecretResetRequest.class);
        identityService.requestSecretReset(request);
        logger.info("Reset password request completed for user:'{}'.", requestDto.getEmail());
        return new ApiResponseDto();
    }

    @ApiOperation("Set new secret")
    @PUT
    @Path("/secret-reset/secret")
    public ApiResponseDto secretReset(@Valid SecretResetRequestDto requestDto) {
        SecretResetRequest request = mapper.map(requestDto, SecretResetRequest.class);
        identityService.secretReset(request);
        logger.info("Reset secret was done by token:'{}'.", requestDto.getToken());
        return new ApiResponseDto();
    }
}
