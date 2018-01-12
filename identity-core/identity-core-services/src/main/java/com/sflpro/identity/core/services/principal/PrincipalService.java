package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Principal;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface PrincipalService {

    /**
     * Gets principal by name
     *
     * @param name principal name
     * @return principal details
     */
    Principal get(final String name, final PrincipalType type);

    /**
     * Gets principal by credential
     *
     * @param credential credential
     * @return principal details
     */
    Principal getByCredentialAndType(final Credential credential, final PrincipalType principalType);

    /**
     * Gets principal by identity
     *
     * @param identity identity
     * @return principal details
     */
    Principal getByIdentityAndType(final Identity identity, final PrincipalType principalType);
}
