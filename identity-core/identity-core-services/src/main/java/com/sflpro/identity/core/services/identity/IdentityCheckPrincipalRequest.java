package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.datatypes.PrincipalType;

/**
 * Company: SFL LLC
 * Created on 16/02/2018
 *
 * @author Davit Harutyunyan
 */
public class IdentityCheckPrincipalRequest {

    private String identityId;

    private PrincipalType principalType;

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
