package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.core.datatypes.PrincipalType;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 16/02/2018
 *
 * @author Davit Harutyunyan
 */
public class IdentityCheckPrincipalRequestDto {

    @NotNull
    private String identityId;

    @NotNull
    private PrincipalType principalType;

    @NotNull
    private String principalName;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(PrincipalType principalType) {
        this.principalType = principalType;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}
