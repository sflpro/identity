package com.sflpro.identity.core.services.auth.impl;

import com.sflpro.identity.core.datatypes.AuthenticationStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.*;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.identity.core.services.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticatorRegistry authenticatorRegistry;

    private final PrincipalService principalService;

    private final TokenService tokenService;

    @Autowired
    public AuthenticationServiceImpl(AuthenticatorRegistry authenticatorRegistry, PrincipalService principalService, TokenService tokenService) {
        this.authenticatorRegistry = authenticatorRegistry;
        this.principalService = principalService;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public <T extends Credential, E extends CredentialIdentifier<T>, S extends AuthenticationRequestDetails<T, E>> AuthenticationResponse authenticate(AuthenticationRequest<T, E, S> request) throws AuthenticationServiceException {
        S details = request.getDetails();
        Class<AuthenticationRequestDetails<T, E>> clazz = request.getDetails().getGenericClass();
        logger.debug("Attempting find identity with credential type{'{}'}.", details.getCredentialType());

        T credential = authenticatorRegistry.getCredentialStore(details.getCredentialIdentifier().getGenericClass()).get(details.getCredentialIdentifier());

        Authenticator<T, E, AuthenticationRequestDetails<T, E>> authenticator = authenticatorRegistry.getAuthenticator(clazz);
        if (authenticator.getSupportedCredentialType() != details.getCredentialType()) {
            throw new IllegalStateException(String.format("Not supported credential type for request %s", request.getCredentialType()));
        }
        AuthenticationResponse authenticationResponse = authenticator.authenticate(credential, details);
        if (authenticationResponse.getStatus() == AuthenticationStatus.AUTHENTICATED) {
            authenticationResponse.setPrincipal(principalService.getByIdentityAndType(credential.getIdentity(), PrincipalType.MAIL));
            if (request.getTokenRequests().size() > 0) {
                List<Token> tokens = tokenService.createNewTokens(request.getTokenRequests(), credential);
                authenticationResponse.setTokens(tokens);
            }
        }
        authenticationResponse.setCredentialTypeUsed(credential.getType());
        authenticationResponse.setIdentity(credential.getIdentity());
        // authenticationResponse.setPermissions(credential.getIdentity().getRoles().get(0).getPermissions()); TODO work on this
        return authenticationResponse;
    }
}
