package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.db.entities.Token;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class InvalidTokenException extends TokenServiceException {

    private static final long serialVersionUID = -281916502929568267L;
    private final transient Token token;
    private final InvalidTokenState tokenState;

    public InvalidTokenException(Token token, InvalidTokenState tokenState) {
        super("Token is invalid state.");

        this.token = token;
        this.tokenState = tokenState;
    }

    public Token getToken() {
        return token;
    }

    public InvalidTokenState getTokenState() {
        return tokenState;
    }
}
