package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityCreationResponseDto extends AbstractApiResponse {

    private String identityId;

    public IdentityCreationResponseDto() {
        super();
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

}
