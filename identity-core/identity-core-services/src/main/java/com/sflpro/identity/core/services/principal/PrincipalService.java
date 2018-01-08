package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.db.entities.Principal;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface PrincipalService {
    Principal get(String name);
}
