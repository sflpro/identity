package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Token;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface TokenService {

    /**
     * Gets token
     *
     * @param tokenType the type of the token
     * @param tokenValue the value of the token
     * @return founder entity
     */
    Token get(final TokenType tokenType, final String tokenValue);

    /**
     * Creates new token
     *
     * @param tokenRequest token data to be generated
     * @param credential the credential token are issued by
     * @return created entity
     */
    Token createNewToken(final TokenRequest tokenRequest, final Credential credential);

    /**
     * Creates new token
     *
     * @param tokens the list of the tokens requested to be created
     * @param credential the credential tokens are issued by
     * @return created entity
     */
    List<Token> createNewTokens(final List<TokenRequest> tokens, final Credential credential);

    /**
     * Check token for existence and validates it
     *
     * @param tokenExistenceCheckRequest the details for token
     * @return validated token entity
     */
    Token getExistingToken(final TokenExistenceCheckRequest tokenExistenceCheckRequest) throws TokenServiceException;

    /**
     * Mark token as used
     *
     * @param tokenInvalidationRequest the details for token
     * @return demarked token entity
     */
    Token invalidateToken(final TokenInvalidationRequest tokenInvalidationRequest) throws TokenServiceException;
}
