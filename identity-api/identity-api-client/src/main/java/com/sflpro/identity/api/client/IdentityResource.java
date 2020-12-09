package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.identity.*;
import com.sflpro.identity.api.common.dtos.identity.reset.RequestSecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetResponseDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public class IdentityResource extends AbstractApiResource {

    public IdentityResource(final Client client, final WebTarget rootTarget) {
        super(client, rootTarget, "/identities");
    }

    public IdentityDto getIdentity(final String id, final Map<String, String> headers) {
        return doGetWithHeaders(id, headers, IdentityDto.class);
    }

    public IdentityDto update(final String identityId, final IdentityUpdateRequestDto requestDto, final Map<String, String> headers) {
        return doPutWithHeaders("/" + identityId, requestDto, headers, IdentityDto.class);
    }

    public ApiResponseDto delete(final String identityId, final Map<String, String> headers) {
        return doDeleteWithHeaders(URI_DELIMITER + identityId, headers, ApiResponseDto.class);
    }

    public IdentityWithTokenDto create(final IdentityCreationRequestDto request, final Map<String, String> headers) {
        return doPutWithHeaders(URI_DELIMITER, request, headers, IdentityWithTokenDto.class);
    }

    public ApiGenericListResponse<ResourceDto> listResources(final String identityId, final String resourceType, final String resourceIdentifier, final Map<String, String> headers) {
        final Map<String, String> params = new HashMap<>();
        Optional.ofNullable(resourceType)
                .ifPresent(t -> params.put("resourceType", resourceType));
        Optional.ofNullable(resourceIdentifier)
                .ifPresent(i -> params.put("resourceIdentifier", resourceIdentifier));
        return doGetWithQueryParamsAndHeaders(String.format("/%s/resources", identityId), params, headers, new GenericType<ApiGenericListResponse<ResourceDto>>() {
        });
    }

    public ApiGenericListResponse<ResourceDto> updateIdentityResources(final String identityId, final IdentityResourceUpdateRequestDto updateRequestDto, final Map<String, String> headers) {
        return doPutWithHeaders(String.format("/%s/resources", identityId), updateRequestDto, headers, new GenericType<ApiGenericListResponse<ResourceDto>>() {
        });
    }

    public ApiResponseDto updateIdentityRoles(final String identityId, final Set<RoleAdditionRequestDto> roleAdditionRequestDtos, final Map<String, String> headers) {
        return doPutWithHeaders(String.format("/%s/roles", identityId), roleAdditionRequestDtos, headers, ApiResponseDto.class);
    }

    public ApiResponseDto secretReset(final SecretResetRequestDto secretResetRequestDto) {
        return doPut("/secret-reset/secret", secretResetRequestDto, ApiResponseDto.class);
    }

    public SecretResetResponseDto requestSecretReset(final RequestSecretResetRequestDto requestSecretResetRequestDto, final Map<String, String> headers) {
        return doPutWithHeaders("/secret-reset/request-token", requestSecretResetRequestDto, headers, SecretResetResponseDto.class);
    }
}