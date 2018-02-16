package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.identity.IdentityCheckPrincipalRequestDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityDto;

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
        super(client, rootTarget, "/identity");
    }

    public IdentityDto getIdentity(String id) {
        return doGet(id, IdentityDto.class);
    }

    public IdentityDto checkIfPrincipalBusy(final IdentityCheckPrincipalRequestDto requestDto) {
        return doPost("/check-principal", requestDto, IdentityDto.class);
    }
}
