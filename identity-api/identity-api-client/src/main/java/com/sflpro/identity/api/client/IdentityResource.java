package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.identity.*;
import com.sflpro.identity.api.common.dtos.identity.reset.RequestSecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetRequestDto;
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

    public ApiResponseDto delete(final String identityId) {
        return doDelete("/" + identityId, ApiResponseDto.class);
    }

    public IdentityWithTokenDto create(final IdentityCreationRequestDto request) {
        return doPut("/", request, IdentityWithTokenDto.class);
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

    public ApiResponseDto secretReset(SecretResetRequestDto secretResetRequestDto) {
        return doPut("/secret-reset/secret", secretResetRequestDto, ApiResponseDto.class);
    }

    public ApiResponseDto requestSecretReset(RequestSecretResetRequestDto requestSecretResetRequestDto) {
        return doPut("/secret-reset/request-token", requestSecretResetRequestDto, ApiResponseDto.class);
    }
}