package com.sflpro.identity.api.common.dtos.principal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sflpro.identity.api.common.dtos.credential.CredentialDto;
import com.sflpro.identity.core.datatypes.PrincipalStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;

/**
 * Company: SFL LLC
 * Created on 12/01/2018
 *
 * @author Davit Harutyunyan
 */
@JsonPropertyOrder({"name", "principalType", "principalStatus"})
public class PrincipalDto extends CredentialDto {

    private String name;

    private PrincipalType principalType;

    private PrincipalStatus principalStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
