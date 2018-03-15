package com.sflpro.identity.core.datatypes;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
public enum PrincipalType {
    MAIL(true), PHONE(true), USERNAME(true);

    private final boolean mainStatusRequired;

    PrincipalType(boolean mainStatusRequired) {
        this.mainStatusRequired = mainStatusRequired;
    }

    public boolean isMainStatusRequired() {
        return mainStatusRequired;
    }
}
