package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.ApiGenericListResponse;
import com.sflpro.identity.api.common.dtos.principal.PrincipalDto;
import com.sflpro.identity.api.common.dtos.principal.PrincipalUpdateRequestDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.Map;

/**
 * Company: SFL LLC
 * Created on 19/02/2018
 *
 * @author Davit Harutyunyan
 */
public class PrincipalResource extends AbstractApiResource {

    protected PrincipalResource(Client client, WebTarget rootTarget) {
        super(client, rootTarget, "/principals");
    }

    public ApiGenericListResponse<PrincipalDto> getAllByIdentity(final String identityId, final Map<String, String> headers) {
        return doGetWithHeaders(URI_DELIMITER + identityId, headers, new GenericType<ApiGenericListResponse<PrincipalDto>>() {
        });
    }

    public ApiGenericListResponse<PrincipalDto> update(final String identityId, final PrincipalUpdateRequestDto requestDto, final Map<String, String> headers) {
        return doPutWithHeaders(URI_DELIMITER + identityId, requestDto, headers, new GenericType<ApiGenericListResponse<PrincipalDto>>() {
        });
    }
}
