package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.mechanism.token.TokenAuthenticationRequestDetails;
import com.sflpro.identity.core.services.resource.ResourceRequest;

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
     * @param tokenRequest token data to be generated
     * @param credential the credential token are issued by
     * @param resourceRequests request for resource access for credential
     * @return created entity
     */
    Token createNewToken(final TokenRequest tokenRequest, final Credential credential, final List<ResourceRequest> resourceRequests);

    /**
     * Creates new token
     *
     * @param tokens the list of the tokens requested to be created
     * @param credential the credential tokens are issued by
     * @param resourceRequests for resource access for credential
     * @return created entity
     */
    List<Token> createNewTokens(final List<TokenRequest> tokens, final Credential credential, final List<ResourceRequest> resourceRequests);

    /**
     * Check token for existence and validates it
     *
     * @param tokenExistenceCheckRequest the details for token
     * @return validated token entity
     * @throws TokenServiceException token service exception
     */
    Token getExistingToken(final TokenExistenceCheckRequest tokenExistenceCheckRequest) throws TokenServiceException;

    /**
     * Mark token as used
     *
     * @param tokenInvalidationRequest the details for token
     * @return demarked token entity
     * @throws TokenServiceException token service exception
     */
    Token invalidateToken(final TokenInvalidationRequest tokenInvalidationRequest) throws TokenServiceException;

    /**
     * Oauth2 .well-known/jwks.json implementation
     *
     * @return
     */
    Object wellKnownJwks();

    /**
     * Rotate token for provided request
     *
     * @param request token rotation request
     */
    Token rotateToken(TokenAuthenticationRequestDetails request) throws InvalidTokenException;
}
