package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.services.identity.IdentityServiceException;

/**
 * Company: SFL LLC
 * Created on 17/02/2018
 *
 * @author Davit Harutyunyan
 */
public class PrincipalNameBusyException extends IdentityServiceException {

    private String name;

    public PrincipalNameBusyException(PrincipalType principalType, String name) {
        super(String.format("Princpal type %s with name %s already exists.", principalType, name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
