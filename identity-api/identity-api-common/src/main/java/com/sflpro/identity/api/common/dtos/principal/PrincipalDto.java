package com.sflpro.identity.api.common.dtos.principal;

import com.sflpro.identity.api.common.dtos.credential.CredentialDto;
import com.sflpro.identity.core.datatypes.PrincipalType;
import io.swagger.annotations.ApiModel;

/**
 * Company: SFL LLC
 * Created on 12/01/2018
 *
 * @author Davit Harutyunyan
 */
@ApiModel(value = "Principal details", parent = CredentialDto.class)
public class PrincipalDto extends CredentialDto {

    private String name;

    private PrincipalType principalType;

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
}
