package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.services.token.TokenInvalidationRequest;
import com.sflpro.identity.core.services.token.TokenServiceException;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface AuthenticationService {

    /**
     * Authenticates request
     *
     * @param authRequest auth request details and token request list
     * @param <T> credential type
     * @param <E> credential identifier
     * @param <S> credential details
     * @return authenticated identity, permissions and tokens list
     * @throws AuthenticationServiceException auth service exceptions
     */
    <T extends Credential, E extends CredentialIdentifier<T>, S extends AuthenticationRequestDetails<T, E>> AuthenticationResponse authenticate(AuthenticationRequest<T, E, S> authRequest) throws AuthenticationServiceException;

    /**
     * Invalidate request
     *
     * @param tokenInvalidationRequest auth request details and token request list
     * @throws TokenServiceException auth service exceptions
     */
    void invalidateToken(TokenInvalidationRequest tokenInvalidationRequest) throws TokenServiceException;

}

