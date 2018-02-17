package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.identity.IdentityDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityUpdateRequestDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public class IdentityResource extends AbstractApiResource {

    IdentityResource(Client client, WebTarget rootTarget) {
        super(client, rootTarget, "/identities");
    }

    public IdentityDto getIdentity(String id) {
        return doGet(id, IdentityDto.class);
    }

    public IdentityDto update(final String identitId, final IdentityUpdateRequestDto requestDto) {
        return doPut("/" + identitId, requestDto, IdentityDto.class);
    }
}
