package com.sflpro.identity.core.services.token.impl;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.db.entities.Role;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.db.repositories.TokenRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.resource.ResourceRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import com.sflpro.identity.core.services.token.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenGenerator.class);

    @Value("${token.generation.strategy}")
    private String generationStrategy;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public Token get(final TokenType tokenType, final String tokenValue) {
        return tokenRepository.findByTokenTypeAndValue(tokenType, tokenValue)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Token with type: %s and value: %s not found", tokenType, tokenValue)));
    }

    @Override
    public Token createNewToken(final TokenRequest tokenRequest, final Credential credential, final List<ResourceRequest> resourceRequests) {
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

        final TokenGenerationRequest tokenGenerationRequest = new TokenGenerationRequest();
        tokenGenerationRequest.setExpiresIn(tokenRequest.getExpiresInHours() * 3600L);
        tokenGenerationRequest.setIdentityId(credential.getIdentity().getId());
        tokenGenerationRequest.setRoles(Optional.ofNullable(credential.getIdentity().getRoles()).orElse(Set.of())
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        if (resourceRequests != null && !resourceRequests.isEmpty()) {
            List<Resource> resources = resourceService.get(resourceRequests, credential.getIdentity());
            final Map<String, List<String>> resourceMap = resources.stream()
                    .collect(Collectors.groupingBy(Resource::getType, Collectors.mapping(Resource::getIdentifier, Collectors.toList())));
            tokenGenerationRequest.setResources(resourceMap);
        }

        Token token = new Token(tokenGenerator.generate(tokenGenerationRequest), tokenRequest.getTokenType(),
                currentLocalDateTime.plus(Duration.ofHours(tokenRequest.getExpiresInHours())), credential);

        token.setType(CredentialType.TOKEN);
        token.setIdentity(credential.getIdentity());
        token.setFailedAttempts(0); // TODO work here

        tokenRepository.save(token);

        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Token> createNewTokens(final List<TokenRequest> tokens, final Credential credential, final List<ResourceRequest> resourceRequests) {
        logger.trace("Creating token for request:{}, credential:{}, resourceRequests:{}", tokens, credential, resourceRequests);
        return tokens.stream()
                .map(tokenRequest -> createNewToken(tokenRequest, credential, resourceRequests))
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Object wellKnownJwks() {
        logger.trace("Getting well known jwk...");
        if ("jwt".equals(generationStrategy)) {
            return ((JwtTokenGenerator) tokenGenerator).jwks();
        }
        logger.debug("Done getting well known jwk...");
        return null;
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
