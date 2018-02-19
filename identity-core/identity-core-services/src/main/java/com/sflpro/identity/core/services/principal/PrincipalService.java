package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;

import java.util.List;

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
     * @param type principal type
     * @param name principal name
     * @return principal details
     */
    Principal get(final PrincipalType type, final String name);

    /** Updates existing principal or inserts a new one if principal absent
     * @param identityId identity id
     * @param updateRequest principal update data
     */
    List<Principal> update(final String identityId, final PrincipalUpdateRequest updateRequest) throws AuthenticationServiceException;
}
