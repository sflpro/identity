package com.sflpro.identity.core.services.token.impl;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.db.repositories.TokenRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.token.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public Token get(final TokenType tokenType, final String tokenValue) {
        return tokenRepository.findByTokenTypeAndValue(tokenType, tokenValue)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Token with type: %s and value: %s not found", tokenType, tokenValue)));
    }

    @Override
    public Token createNewToken(final TokenRequest tokenRequest, final Credential credential) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        Iterable<Token> existingTokens = tokenRepository.findAllByTokenTypeAndIssuedBy(
                tokenRequest.getTokenType(),
                credential
        );

        if (existingTokens.iterator().hasNext()) {
            for (Token existingToken : existingTokens) {
                if (existingToken.getExpirationDate().isAfter(currentLocalDateTime)) {
                    existingToken.setExpirationDate(currentLocalDateTime);
                }
            }
            tokenRepository.saveAll(existingTokens);
        }

        Token token = new Token(tokenGenerator.generate(), tokenRequest.getTokenType(),
                currentLocalDateTime.plus(Duration.ofHours(tokenRequest.getExpiresInHours())), credential);

        token.setType(CredentialType.TOKEN);
        token.setIdentity(credential.getIdentity());
        token.setFailedAttempts(0); // TODO work here

        tokenRepository.save(token);

        return token;
    }

    @Override
    public List<Token> createNewTokens(final List<TokenRequest> tokens, final Credential credential) {
        return tokens.stream()
                .map(tokenRequest -> createNewToken(tokenRequest, credential))
                .collect(Collectors.toList());
    }

    @Override
    public Token getExistingToken(final TokenExistenceCheckRequest tokenExistenceCheckRequest) throws TokenServiceException {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        return this.getExistingValidToken(
                tokenExistenceCheckRequest.getTokenValue(),
                tokenExistenceCheckRequest.getTokenType(),
                currentLocalDateTime
        );
    }

    @Override
    public Token invalidateToken(final TokenInvalidationRequest tokenInvalidationRequest) throws TokenServiceException {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        Token existingValidToken = this.getExistingValidToken(
                tokenInvalidationRequest.getToken(),
                tokenInvalidationRequest.getTokenType(),
                currentLocalDateTime
        );

        existingValidToken.setExpirationDate(currentLocalDateTime); // TODO check with Mr Smith to we need to set used date, instead of making token expired

        return tokenRepository.save(existingValidToken);
    }

    private Token getExistingValidToken(final String tokenValue, final TokenType tokenType, final LocalDateTime requestLocalDateTime) throws InvalidTokenException {
        Assert.notNull(tokenValue, "tokenValue should not be null.");
        Assert.notNull(tokenType, "tokenType should not be null.");
        Assert.notNull(requestLocalDateTime, "requestLocalDateTime should not be null.");

        Token existingToken = tokenRepository.findByTokenTypeAndValue(tokenType, tokenValue)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Token with type: %s and value: %s not found", tokenType, tokenValue)));

        if (existingToken.getDeleted() != null) {
            throw new InvalidTokenException(existingToken, InvalidTokenState.DELETED);
        }

        if (requestLocalDateTime.isBefore(existingToken.getCreated())) {
            throw new IllegalStateException("Token has a creation date in future.");
        }

        if (requestLocalDateTime.isAfter(existingToken.getExpirationDate())) {
            throw new InvalidTokenException(existingToken, InvalidTokenState.EXPIRED);
        }

        return existingToken;
    }
}
