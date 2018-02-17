package com.sflpro.identity.api.common.dtos.principal;

import com.sflpro.identity.core.datatypes.PrincipalStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;

/**
 * Company: SFL LLC
 * Created on 17/02/2018
 *
 * @author Davit Harutyunyan
 */
public class PrincipalUpdateRequestDto {

    private PrincipalType principalType;

    private PrincipalStatus principalStatus;

    private String principalName;

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(PrincipalType principalType) {
        this.principalType = principalType;
    }

    public PrincipalStatus getPrincipalStatus() {
        return principalStatus;
    }

    public void setPrincipalStatus(PrincipalStatus principalStatus) {
        this.principalStatus = principalStatus;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}
