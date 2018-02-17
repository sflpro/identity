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

    private PrincipalType principalType;

    private String name;

    public PrincipalNameBusyException(String message) {
        super(message);
    }

    public PrincipalNameBusyException(PrincipalType principalType, String name) {
        super(String.format("Princpal type %s with name %s already exists.", principalType, name));
        this.principalType = principalType;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
