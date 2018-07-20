package com.sflpro.identity.api.common.dtos.resource;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 7/20/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceIdentityAdditionRequestDto {

    @NotEmpty
    private Set<String> identityIds;

    public Set<String> getIdentityIds() {
        return identityIds;
    }

    public void setIdentityIds(Set<String> identityIds) {
        this.identityIds = identityIds;
    }
}
