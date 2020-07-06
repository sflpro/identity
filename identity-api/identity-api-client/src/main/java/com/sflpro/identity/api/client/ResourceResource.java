package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import com.sflpro.identity.api.common.dtos.identity.IdentityDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceCreationRequestDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceDto;
import com.sflpro.identity.api.common.dtos.resource.ResourceUpdateRequestDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.Map;

/**
 * Company: SFL LLC
 * Created on 7/19/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceResource extends AbstractApiResource {
    protected ResourceResource(Client client, WebTarget rootTarget) {
        super(client, rootTarget, "/resources");
    }

    public ResourceDto create(final ResourceCreationRequestDto creationRequestDto, final Map<String, String> headers) {
        return doPutWithHeaders("", creationRequestDto, headers, ResourceDto.class);
    }

    public ResourceDto get(final long resourceId, final Map<String, String> headers) {
        return doGetWithHeaders(Long.toString(resourceId), headers, ResourceDto.class);
    }

    public ResourceDto update(final long resourceId, final ResourceUpdateRequestDto updateRequestDto, final Map<String, String> headers) {
        return doPutWithHeaders(Long.toString(resourceId), updateRequestDto, headers, ResourceDto.class);
    }

    public ApiResponseDto delete(final long resourceId, final Map<String, String> headers) {
        return doDeleteWithHeaders(Long.toString(resourceId), headers, ApiResponseDto.class);
    }

    public ApiGenericListResponse<IdentityDto> listIdentities(final long resourceId, final Map<String, String> headers) {
        return doGetWithHeaders(String.format("/%s/identities", resourceId), headers, new GenericType<ApiGenericListResponse<IdentityDto>>() {
        });
    }
}
