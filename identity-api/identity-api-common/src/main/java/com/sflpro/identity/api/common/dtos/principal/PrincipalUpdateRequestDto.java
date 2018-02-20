package com.sflpro.identity.api.common.dtos.principal;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 19/02/2018
 *
 * @author Davit Harutyunyan
 */
public class PrincipalUpdateRequestDto {

    @NotNull
    private String secret;

    @NotNull
    private List<PrincipalUpdateDetailsRequestDto> updateDetailsRequests;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<PrincipalUpdateDetailsRequestDto> getUpdateDetailsRequests() {
        return updateDetailsRequests;
    }

    public void setUpdateDetailsRequests(List<PrincipalUpdateDetailsRequestDto> updateDetailsRequests) {
        this.updateDetailsRequests = updateDetailsRequests;
    }
}
