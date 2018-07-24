package com.sflpro.identity.api.common.dtos.identity;

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
    private List<Long> resourceIds;

    public List<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
