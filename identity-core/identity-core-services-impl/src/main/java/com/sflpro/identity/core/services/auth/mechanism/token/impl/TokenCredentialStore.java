package com.sflpro.identity.core.services.auth.mechanism.token.impl;

import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.CredentialStore;
import com.sflpro.identity.core.services.auth.mechanism.token.TokenCredentialIdentifier;
import com.sflpro.identity.core.services.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Company: SFL LLC
 * Created on 01/12/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class TokenCredentialStore implements CredentialStore<Token, TokenCredentialIdentifier> {

    private final TokenService tokenService;

    @Autowired
    public TokenCredentialStore(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Token get(TokenCredentialIdentifier identifier) {
        return tokenService.get(identifier.getTokenType(), identifier.getToken());
    }

    @Override
    public Class<TokenCredentialIdentifier> getCredentialIdentifierType() {
        return TokenCredentialIdentifier.class;
    }
}