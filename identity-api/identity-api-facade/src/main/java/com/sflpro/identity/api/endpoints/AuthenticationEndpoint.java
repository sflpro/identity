package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;
import com.sflpro.identity.api.common.dtos.auth.*;
import com.sflpro.identity.api.common.dtos.identity.InactiveIdentityExceptionDtoDto;
import com.sflpro.identity.api.common.dtos.token.TokenDto;
import com.sflpro.identity.api.common.dtos.token.TokenInvalidationRequestDto;
import com.sflpro.identity.api.common.dtos.token.TokenRotationRequestDetailsDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.*;
import com.sflpro.identity.core.services.auth.mechanism.token.TokenAuthenticationRequestDetails;
import com.sflpro.identity.core.services.identity.InactiveIdentityException;
import com.sflpro.identity.core.services.token.TokenExpiredException;
import com.sflpro.identity.core.services.token.TokenInvalidationRequest;
import com.sflpro.identity.core.services.token.TokenService;
import com.sflpro.identity.core.services.token.TokenServiceException;
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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    private TokenService tokenService;

    @ApiOperation("Authenticating by credential type")
    @POST
    @Path("/authenticate")
    @Transactional(noRollbackFor = {IdentityApiExceptionDto.class})
    public <T extends AuthenticationRequestDetailsDto> AuthenticationResponseDto authenticate(@Valid AuthenticationRequestDto<T> requestDto) {
        Assert.notNull(requestDto, "request cannot be null");
        Assert.notNull(requestDto.getDetails(), "request details cannot be null");
        logger.debug("Authenticating by credential type: {}...", requestDto.getDetails().getCredentialType());
        //Compose authentication request
        AuthenticationRequest<?, ?, ?> authRequest = mapper.map(requestDto, AuthenticationRequest.class);
        //Try to authenticate
        try {
            AuthenticationResponse authResponse = authService.authenticate(authRequest);
            logger.info("Done authenticating by credential type:'{}'.", requestDto.getDetails().getCredentialType());
            return mapper.map(authResponse, AuthenticationResponseDto.class);
        } catch (InactiveIdentityException e) {
            logger.warn("Authentication failed for inactive identity request:'{}'.", requestDto);
            throw new InactiveIdentityExceptionDtoDto(e);
        } catch (TokenExpiredException e) {
            logger.warn("Authentication failed for expired token request:'{}'.", requestDto);
            throw new TokenExpiredExceptionDto(e.getMessage(), e);
        } catch (AuthenticationAttemptLimitReachedException e) {
            logger.warn("Authentication failed for expired token request:'{}'.", requestDto);
            throw new AuthenticationAttemptLimitReachedExceptionDto(e.getMessage(), e);
        } catch (AuthenticationServiceException e) {
            logger.warn("Authentication failed for request:'{}'.", requestDto);
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

    @ApiOperation("Roteate token")
    @POST
    @Path("/rotate-token")
    @Transactional
    public TokenDto rotateToken(@Valid TokenRotationRequestDetailsDto requestDto) {
        Assert.notNull(requestDto, "request cannot be null");
        Assert.notNull(requestDto.getToken(), "request token cannot be null");
        Assert.notNull(requestDto.getTokenType(), "request token type cannot be null");
        logger.debug("Rotating token:{}...", requestDto.getToken());
        //Compose authentication request
        final TokenAuthenticationRequestDetails request = mapper.map(requestDto, TokenAuthenticationRequestDetails.class);
        //Try to invalidate token
        try {
            final Token token = tokenService.rotateToken(request);
            logger.info("Done rotating token:'{}'.", requestDto.getToken());
            return mapper.map(token, TokenDto.class);
        } catch (TokenServiceException e) {
            logger.warn("Rotating token failed for request:{}.", requestDto);
            throw new AuthenticationExceptionDto(e.getMessage(), e);
        }
    }

    @ApiOperation("Invalidate token")
    @GET
    @Path("/.well-known/jwks.json")
    public JwksResponseDto wellKnownJwks() {
        logger.debug("Getting well known jwks...");
        final Object jwk = tokenService.wellKnownJwks();
        logger.info("Done getting well known jwks...");
        return new JwksResponseDto(Set.of(jwk));
    }
}
