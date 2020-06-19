package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDetailsDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationRequestDto;
import com.sflpro.identity.api.common.dtos.auth.AuthenticationResponseDto;
import com.sflpro.identity.api.common.dtos.token.TokenDto;
import com.sflpro.identity.api.common.dtos.token.TokenRotationRequestDetailsDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Company: SFL LLC
 * Created on 19/06/2020
 *
 * @author Davit Harutyunyan
 */
public class AuthResource extends AbstractApiResource {

    AuthResource(Client client, WebTarget rootTarget) {
        super(client, rootTarget, "/auth");
    }

    public <T extends AuthenticationRequestDetailsDto> AuthenticationResponseDto authenticate(AuthenticationRequestDto<T> requestDto) {
        return doPost("/authenticate", requestDto, AuthenticationResponseDto.class);
    }

    public TokenDto rotateToken(final TokenRotationRequestDetailsDto tokenRotationRequestDetailsDto) {
        return doPost("/rotate-token", tokenRotationRequestDetailsDto, TokenDto.class);
    }
}
