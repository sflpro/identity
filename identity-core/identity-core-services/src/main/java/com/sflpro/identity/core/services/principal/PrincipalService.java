package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.datatypes.PrincipalType;
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

    /** Updates existing principal or inserts a new one if principal absent
     * @param identity identity
     * @param updateRequest update data
     */
    Principal upsert(final Identity identity, final PrincipalUpdateRequest updateRequest);;
}
