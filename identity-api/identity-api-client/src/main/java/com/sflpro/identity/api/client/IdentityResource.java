package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.identity.IdentityDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityUpdateRequestDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityResourceUpdateRequestDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public IdentityDto update(final String identityId, final IdentityUpdateRequestDto requestDto) {
        return doPut("/" + identityId, requestDto, IdentityDto.class);
    }

    public ApiGenericListResponse<ResourceDto> listResources(final String identityId, final String resourceType, final String resourceIdentifier) {
        Map<String, String> params = new HashMap<>();
        Optional.ofNullable(resourceType)
                .ifPresent(t -> params.put("resourceType", resourceType));
        Optional.ofNullable(resourceIdentifier)
                .ifPresent(i -> params.put("resourceIdentifier", resourceIdentifier));
        return doGet(String.format("/%s/resources", identityId), new GenericType<ApiGenericListResponse<ResourceDto>>(){}, params);
    }

    public ApiGenericListResponse<ResourceDto> updateIdentityResources(final String identityId, IdentityResourceUpdateRequestDto updateRequestDto) {
        return doPut(String.format("/%s/resources", identityId), updateRequestDto, new GenericType<ApiGenericListResponse<ResourceDto>>(){});
    }
}