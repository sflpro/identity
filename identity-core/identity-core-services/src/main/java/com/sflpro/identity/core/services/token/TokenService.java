package com.sflpro.identity.core.services.token;

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
    Token get(final String tokenType, final String tokenValue);

    /**
     * Creates new token
     *
     * @param tokenRequest token data to be generated
     * @param credentialId the id of credential token are issued by
     * @return created entity
     */
    Token createNewToken(final TokenRequest tokenRequest, final String credentialId);

    /**
     * Creates new token
     *
     * @param tokens the list of the tokens requested to be created
     * @param credentialId the id of credential tokens are issued by
     * @return created entity
     */
    List<Token> createNewTokens(final List<TokenRequest> tokens, final String credentialId);

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
     * @param tokenDemarcationAsUsedRequest the details for token
     * @return demarked token entity
     */
    Token demarkTokenAsUsed(final TokenDemarcationAsUsedRequest tokenDemarcationAsUsedRequest) throws TokenServiceException;
}
