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

    public ResourceDto create(final ResourceCreationRequestDto creationRequestDto) {
        return doPut("", creationRequestDto, ResourceDto.class);
    }

    public ResourceDto get(final long resourceId) {
        return doGet(Long.toString(resourceId), ResourceDto.class);
    }

    public ResourceDto update(final long resourceId, final ResourceUpdateRequestDto updateRequestDto) {
        return doPut(Long.toString(resourceId), updateRequestDto, ResourceDto.class);
    }

    public ApiResponseDto delete(final long resourceId) {
        return doDelete(Long.toString(resourceId), ApiResponseDto.class);
    }

    public ApiGenericListResponse<IdentityDto> listIdentities(final long resourceId){
        return doGet(String.format("/%s/identities", resourceId), new GenericType<ApiGenericListResponse<IdentityDto>>() {});
    }
}
