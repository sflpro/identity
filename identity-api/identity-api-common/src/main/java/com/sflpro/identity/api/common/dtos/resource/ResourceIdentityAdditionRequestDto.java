package com.sflpro.identity.api.common.dtos.resource;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 7/20/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceIdentityAdditionRequestDto {

    private @NotEmpty List<String> identityIds;

    public @NotEmpty List<String> getIdentityIds() {
        return identityIds;
    }

    public void setIdentityIds(@NotEmpty List<String> identityIds) {
        this.identityIds = identityIds;
    }
}
