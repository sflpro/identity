package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.auth.AuthenticationExceptionDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDetailsDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationResponseDto;
import com.sflpro.identity.api.common.dtos.identity.InactiveIdentityExceptionDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.services.auth.*;
import com.sflpro.identity.core.services.identity.InactiveIdentityException;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
            logger.info("Done authenticating by credential type:'{}'.", requestDto.getDetails().getCredentialType());
            return mapper.map(authResponse, AuthenticationResponseDto.class);
        } catch (InactiveIdentityException e) {
            logger.warn("Authentication failed for inactive identity request:'{}'.", requestDto);
            throw new InactiveIdentityExceptionDto(e);
        } catch (AuthenticationServiceException e) {
            logger.warn("Authentication failed for request:'{}'.", requestDto);
            throw new AuthenticationExceptionDto(e.getMessage(), e);
        }
    }
}