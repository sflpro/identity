package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationExceptionDto;
import com.sflpro.identity.api.common.dtos.identity.*;
import com.sflpro.identity.api.common.dtos.identity.reset.RequestSecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceDto;
import com.sflpro.identity.api.common.dtos.token.TokenDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.IdentityStatus;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.credential.CredentialCreation;
import com.sflpro.identity.core.services.credential.CredentialService;
import com.sflpro.identity.core.services.identity.IdentityCreationRequest;
import com.sflpro.identity.core.services.identity.IdentityResourceUpdateRequest;
import com.sflpro.identity.core.services.identity.IdentityService;
import com.sflpro.identity.core.services.identity.IdentityUpdateRequest;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import com.sflpro.identity.core.services.token.TokenRequest;
import com.sflpro.identity.core.services.token.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CredentialService credentialService;

    @ApiOperation("Returns identity's details")
    @GET
    @Path("/{identityId}")
    @Transactional(readOnly = true)
    public IdentityDto get(@NotNull @PathParam("identityId") final String identityId) {
        Identity identity = identityService.get(identityId);
        logger.info("Got identity:'{}'.", identity.getId());
        return mapper.map(identity, IdentityDto.class);
    }

    @ApiOperation("Updates identity's details")
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

    @ApiOperation("Delete identity")
    @DELETE
    @Path("/{identityId}")
    @Transactional
    public ApiResponseDto delete(@NotNull @PathParam("identityId") final String identityId) {
        identityService.delete(identityId);
        return new ApiResponseDto();
    }

    @ApiOperation("Request for secret reset")
    @PUT
    @Path("/secret-reset/request-token")
    @Transactional
    public ApiResponseDto requestSecretReset(@Valid RequestSecretResetRequestDto requestDto) {
        if (StringUtils.isEmpty(requestDto.getEmailTemplateName())) {
            requestDto.setEmailTemplateName(emailDefaultTemplateName);
        }
        if (StringUtils.isEmpty(requestDto.getRedirectUri())) {
            requestDto.setRedirectUri(emailRedirectUri);
        }
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

    @ApiOperation("Creates identity's details")
    @PUT
    @Path("/")
    @Transactional
    public IdentityWithTokenDto createIdentity(@NotNull @Valid final IdentityCreationRequestDto request) {
        Assert.notNull(request, "request cannot be null");
        logger.debug("Creating identity  with data :{}...", request);
        final IdentityCreationRequest creationRequest = mapper.map(request, IdentityCreationRequest.class);
        final Identity identity = identityService.add(creationRequest);
        final IdentityWithTokenDto result = mapper.map(identity, IdentityWithTokenDto.class);
        if (identity.getStatus() == IdentityStatus.ACTIVE) {
            final CredentialCreation credentialCreation = new CredentialCreation();
            credentialCreation.setCredentialType(CredentialType.DEFAULT);
            credentialCreation.setDetails("No credential, default token");
            final Credential credential = credentialService.store(identity, credentialCreation);
            final Token token = tokenService.createNewToken(new TokenRequest(TokenType.REFRESH), credential);
            result.setToken(mapper.map(token, TokenDto.class));
        }
        logger.info("Done creating identity with data :{}....", creationRequest);
        return result;
    }

    @ApiOperation("Lists identity's all resources")
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

    @ApiOperation("Update resources of identity")
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
