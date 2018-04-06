package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.db.entities.Identity;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public class InactiveIdentityException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "%s is not active";

    public InactiveIdentityException(Identity identity) {
        super(String.format(MESSAGE_FORMAT, identity.toString()));
    }

}
