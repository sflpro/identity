package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationExceptionDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDetailsDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationResponseDto;
import com.sflpro.identity.api.common.dtos.identity.InactiveIdentityExceptionDtoDto;
import com.sflpro.identity.api.common.dtos.token.TokenInvalidationRequestDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.datatypes.IdentityStatus;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Role;
import com.sflpro.identity.core.services.auth.AuthenticationRequest;
import com.sflpro.identity.core.services.auth.AuthenticationResponse;
import com.sflpro.identity.core.services.auth.AuthenticationService;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.credential.CredentialService;
import com.sflpro.identity.core.services.identity.IdentityService;
import com.sflpro.identity.core.services.identity.IdentityUpdateRequest;
import com.sflpro.identity.core.services.identity.InactiveIdentityException;
import com.sflpro.identity.core.services.token.TokenInvalidationRequest;
import com.sflpro.identity.core.services.token.TokenServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@SwaggerDefinition(tags = {@Tag(name = "auth", description = "Authorization & related")})
@Api(tags = {"auth"})
@Component
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEndpoint.class);

    @Autowired
    private BeanMapper mapper;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    CredentialService credentialService;

    @Autowired
    IdentityService identityService;

    @ApiOperation("Authenticating by credential type")
    @POST
    @Path("/authenticate")
    @Transactional
    public <T extends AuthenticationRequestDetailsDto> AuthenticationResponseDto authenticate(@Valid AuthenticationRequestDto<T> requestDto) {
        Assert.notNull(requestDto, "request cannot be null");
        Assert.notNull(requestDto.getDetails(), "request details cannot be null");
        logger.debug("Authenticating by credential type: {}...", requestDto.getDetails().getCredentialType());
        //Compose authentication request
        AuthenticationRequest authRequest = mapper.map(requestDto, AuthenticationRequest.class);
        //Try to authenticate
        try {
            AuthenticationResponse authResponse = authService.authenticate(authRequest);
            final List<Role> roles = authResponse.getIdentity().getRoles();
            final Set<String> permissions = new HashSet<>();
            roles.forEach(role ->
                    role.getPermissions().forEach(permission ->
                            permissions.add(permission.getName())
                    )
            );
            authResponse.setPermissions(permissions);
            logger.info("Done authenticating by credential type:'{}'.", requestDto.getDetails().getCredentialType());
            return mapper.map(authResponse, AuthenticationResponseDto.class);
        } catch (InactiveIdentityException e) {
            logger.warn("Authentication failed for inactive identity request:'{}'.", requestDto);
            throw new InactiveIdentityExceptionDtoDto(e);
        } catch (AuthenticationServiceException e) {
            logger.warn("Authentication failed for request:'{}'.", requestDto);
            final Credential credential = authService.getCredential(authRequest);
            credential.setFailedAttempts(credential.getFailedAttempts() + 1);
            Credential update = credentialService.update(credential);
            throw new AuthenticationExceptionDto(e.getMessage(), e);
        }
    }

    @ApiOperation("Invalidate token")
    @POST
    @Path("/invalidate-token")
    @Transactional
    public ApiResponseDto invalidateToken(@Valid TokenInvalidationRequestDto requestDto) {
        Assert.notNull(requestDto, "request cannot be null");
        Assert.notNull(requestDto.getToken(), "request token cannot be null");
        Assert.notNull(requestDto.getTokenType(), "request token type cannot be null");
        logger.debug("Invalidating token: {}...", requestDto.getToken());
        //Compose authentication request
        TokenInvalidationRequest request = mapper.map(requestDto, TokenInvalidationRequest.class);
        //Try to invalidate token
        try {
            authService.invalidateToken(request);
            logger.info("Done invalidating token:'{}'.", requestDto.getToken());
            return new ApiResponseDto();
        } catch (TokenServiceException e) {
            logger.warn("Invalidating token failed for request:'{}'.", requestDto);
            throw new AuthenticationExceptionDto(e.getMessage(), e);
        }
    }
}
