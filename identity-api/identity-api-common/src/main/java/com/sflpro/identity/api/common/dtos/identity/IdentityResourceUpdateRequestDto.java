package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 7/23/2018
 *
 * @author Taron Petrosyan
 */
public class IdentityResourceUpdateRequestDto {

    @NotEmpty
    private List<ResourceRequestDto> resourceRequests;

    public List<ResourceRequestDto> getResourceRequests() {
        return resourceRequests;
    }

    public void setResourceRequests(List<ResourceRequestDto> resourceRequests) {
        this.resourceRequests = resourceRequests;
    }
}
