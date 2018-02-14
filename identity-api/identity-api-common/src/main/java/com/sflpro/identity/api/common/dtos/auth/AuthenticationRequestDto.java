package com.sflpro.identity.api.common.dtos.auth;

import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;
import com.sflpro.identity.api.common.dtos.token.TokenRequestDto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public class AuthenticationRequestDto<T extends AuthenticationRequestDetailsDto> {

    @NotNull
    private T details;

    private List<TokenRequestDto> tokenRequests;

    private List<ResourceRequestDto> resourceRequests;

    public T getDetails() {
        return details;
    }

    public void setDetails(T details) {
        this.details = details;
    }

    public List<TokenRequestDto> getTokenRequests() {
        return tokenRequests;
    }

    public void setTokenRequests(List<TokenRequestDto> tokenRequests) {
        this.tokenRequests = tokenRequests;
    }

    public List<ResourceRequestDto> getResourceRequests() {
        return resourceRequests;
    }

    public void setResourceRequests(List<ResourceRequestDto> resourceRequests) {
        this.resourceRequests = resourceRequests;
    }
}
