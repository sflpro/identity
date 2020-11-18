package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationExceptionDto;
import com.sflpro.identity.api.common.dtos.identity.*;
import com.sflpro.identity.api.common.dtos.identity.reset.RequestSecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetResponseDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.identity.*;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
@Component
@Path("/identities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IdentityEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(IdentityEndpoint.class);

    @Value("${email.default.redirect.uri}")
    private String emailRedirectUri;

    @Value("${email.default.template.name}")
    private String emailDefaultTemplateName;

    @Autowired
    private BeanMapper mapper;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ResourceService resourceService;

    @Operation(tags = {"identities"}, summary = "Returns identity's details")
    @GET
    @Path("/{identityId}")
    @Transactional(readOnly = true)
    public IdentityDto get(@NotNull @PathParam("identityId") final String identityId) {
        Identity identity = identityService.get(identityId);
        logger.info("Got identity:'{}'.", identity.getId());
        return mapper.map(identity, IdentityDto.class);
    }

    @Operation(tags = {"identities"}, summary = "Updates identity's details")
    @PUT
    @Path("/{identityId}")
    @Transactional
    public IdentityDto update(@NotNull @PathParam("identityId") final String identityId,
                              @Valid final IdentityUpdateRequestDto updateRequestDto) {
        Assert.notNull(updateRequestDto, "updateRequestDto cannot be null");
        logger.debug("Updating identity id {} with data :{}...", identityId, updateRequestDto);
        try {
            IdentityUpdateRequest updateRequest = mapper.map(updateRequestDto, IdentityUpdateRequest.class);
            Identity identity = identityService.update(identityId, updateRequest);
            logger.info("Done updating identity id {} with data :{}....", identityId, updateRequestDto);
            return mapper.map(identity, IdentityDto.class);
        } catch (AuthenticationServiceException e) {
            logger.warn("Authentication failed for request:'{}'.", updateRequestDto);
            throw new AuthenticationExceptionDto(e.getMessage(), e);
        }
    }

    @Operation(tags = {"identities"}, summary = "Delete identity")
    @DELETE
    @Path("/{identityId}")
    @Transactional
    public ApiResponseDto delete(@NotNull @PathParam("identityId") final String identityId) {
        identityService.delete(identityId);
        return new ApiResponseDto();
    }

    @Operation(tags = {"identities"}, summary = "Request for secret reset")
    @PUT
    @Path("/secret-reset/request-token")
    @Transactional
    public SecretResetResponseDto requestSecretReset(@Valid RequestSecretResetRequestDto requestDto) {
        if (StringUtils.isEmpty(requestDto.getEmailTemplateName())) {
            requestDto.setEmailTemplateName(emailDefaultTemplateName);
        }
        if (StringUtils.isEmpty(requestDto.getRedirectUri())) {
            requestDto.setRedirectUri(emailRedirectUri);
        }
        RequestSecretResetRequest request = mapper.map(requestDto, RequestSecretResetRequest.class);
        final SecretResetResponse secretResetResponse = identityService.requestSecretReset(request);
        logger.info("Reset password request completed for user:'{}'.", requestDto.getEmail());
        return new SecretResetResponseDto(secretResetResponse.getNotificationId());
    }

    @Operation(tags = {"identities"}, summary = "Set new secret")
    @PUT
    @Path("/secret-reset/secret")
    @Transactional
    public ApiResponseDto secretReset(@Valid SecretResetRequestDto requestDto) {
        SecretResetRequest request = mapper.map(requestDto, SecretResetRequest.class);
        identityService.secretReset(request);
        logger.info("Reset secret was done by token:'{}'.", requestDto.getToken());
        return new ApiResponseDto();
    }

    @Operation(tags = {"identities"}, summary = "Creates identity's details")
    @PUT
    @Path("/")
    @Transactional
    public IdentityWithTokenDto createIdentity(@NotNull @Valid final IdentityCreationRequestDto request) {
        Assert.notNull(request, "request cannot be null");
        logger.debug("Creating identity  with data :{}...", request);
        final IdentityCreationRequest creationRequest = mapper.map(request, IdentityCreationRequest.class);
        final IdentityResponse identity = identityService.add(creationRequest);
        final IdentityWithTokenDto result = mapper.map(identity, IdentityWithTokenDto.class);
        logger.info("Done creating identity with data :{}....", creationRequest);
        return result;
    }

    @Operation(tags = {"identities"}, summary = "Set roles for provided identity")
    @PUT
    @Path("/{identityId}/roles")
    @Transactional
    public ApiResponseDto setRoles(@NotNull @PathParam("identityId") final String identityId,
                                   @NotNull @Valid final Set<RoleAdditionRequestDto> additionRequests) {
        logger.debug("Adding roles identity  with data :{}...", additionRequests);
        final Set<RoleAdditionRequest> roleAdditionRequests = mapper.mapAsSet(additionRequests, RoleAdditionRequest.class);
        identityService.setRoles(identityId, roleAdditionRequests);
        logger.info("Done adding roles:{} to identity:{}", additionRequests, identityId);
        return new ApiResponseDto();
    }

    @Operation(tags = {"identities"}, summary = "Lists identity's all resources")
    @GET
    @Path("/{identityId}/resources")
    @Transactional(readOnly = true)
    public ApiGenericListResponse<ResourceDto> listResources(@NotNull @PathParam("identityId") final String identityId,
                                                             @QueryParam("resourceType") final String resourceType,
                                                             @QueryParam("resourceIdentifier") final String resourceIdentifier) {
        // Get resources
        List<Resource> resources = resourceService.search(identityId, resourceType, resourceIdentifier);
        logger.info("Found {} resources for identity:'{}'.", resources.size(), identityId);
        List<ResourceDto> result = mapper.mapAsList(resources, ResourceDto.class);
        return new ApiGenericListResponse<>(result.size(), result);
    }

    @Operation(tags = {"identities"}, summary = "Update resources of identity")
    @PUT
    @Path("/{identityId}/resources")
    @Transactional
    public ApiGenericListResponse<ResourceDto> updateIdentityResources(@NotNull @PathParam("identityId") final String identityId, @NotNull @Valid IdentityResourceUpdateRequestDto updateRequestDto) {
        logger.debug("Updating resources of identity: {}", identityId);
        IdentityResourceUpdateRequest updateRequest = mapper.map(updateRequestDto, IdentityResourceUpdateRequest.class);
        updateRequest.setIdentityId(identityId);
        List<Resource> resources = identityService.updateIdentityResources(updateRequest);
        logger.info("Done updating resources of identity: {}", identityId);
        List<ResourceDto> result = mapper.mapAsList(resources, ResourceDto.class);
        return new ApiGenericListResponse<>(result.size(), result);
    }
}
