package com.sflpro.identity.core.services.token.impl;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.db.entities.Role;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.db.repositories.TokenRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.auth.mechanism.token.TokenAuthenticationRequestDetails;
import com.sflpro.identity.core.services.identity.resource.role.IdentityResourceRoleService;
import com.sflpro.identity.core.services.resource.ResourceRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import com.sflpro.identity.core.services.role.RoleService;
import com.sflpro.identity.core.services.token.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    @Value("${jwt.token.generation.strategy.enabled}")
    private boolean jwtStrategyEnabled;

    @Value("${jwt.token.rotation.allowed.offset.in.seconds}")
    private Long allowedOffsetInSeconds;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private IdentityResourceRoleService identityResourceRoleService;

    @Autowired
    @Qualifier("jwtTokenGenerator")
    private TokenGenerator jwtTokenGenerator;

    @Autowired
    @Qualifier("secureRandomTokenGenerator")
    private TokenGenerator secureRandomTokenGenerator;

    @Override
    public Token get(final TokenType tokenType, final String tokenValue) {
        return tokenRepository.findByTokenTypeAndValue(tokenType, tokenValue)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Token with type: %s and value: %s not found", tokenType, tokenValue)));
    }

    @Override
    public Token createNewToken(TokenRequest tokenRequest, Credential credential) {
        return createNewToken(tokenRequest, credential, List.of());
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
                if (existingToken.getExpirationDate() != null && existingToken.getExpirationDate().isAfter(currentLocalDateTime)) {
                    existingToken.setExpirationDate(currentLocalDateTime);
                }
            }
            tokenRepository.saveAll(existingTokens);
        }

        final TokenGenerationRequest tokenGenerationRequest = new TokenGenerationRequest();
        tokenGenerationRequest.setExpiresIn(tokenRequest.getExpiresInHours() == null ? null : tokenRequest.getExpiresInHours() * 3600L);
        final String identityId = credential.getIdentity().getId();
        tokenGenerationRequest.setIdentityId(identityId);
        
        if (CollectionUtils.isEmpty(resourceRequests)) {
            List<Resource> resources = resourceService.get(resourceRequests, credential.getIdentity());
            final Map<String, List<String>> resourceMap = resources.stream()
                    .collect(Collectors.groupingBy(Resource::getType, Collectors.mapping(Resource::getIdentifier, Collectors.toList())));
            tokenGenerationRequest.setRoles(CollectionUtils.isEmpty(resources) ? Collections.emptySet() : resources.parallelStream()
                    .map(resource -> identityResourceRoleService.getAllByIdentityIdAndResourceId(identityId, resource.getId()))
                    .flatMap(identityResourceRoles -> identityResourceRoles.parallelStream()
                            .map(identityResourceRole -> roleService.get(identityResourceRole.getRoleId()))
                            .map(Role::getName)
                    ).collect(Collectors.toUnmodifiableSet()));
            tokenGenerationRequest.setResources(resourceMap);
        }
        final String generatedToken;
        if (jwtStrategyEnabled && tokenRequest.getTokenType() == TokenType.ACCESS) {
            generatedToken = jwtTokenGenerator.generate(tokenGenerationRequest);
        } else {
            generatedToken = secureRandomTokenGenerator.generate(tokenGenerationRequest);
        }
        Token token = new Token(generatedToken, tokenRequest.getTokenType(),
                tokenRequest.getExpiresInHours() == null ? null : currentLocalDateTime.plus(Duration.ofHours(tokenRequest.getExpiresInHours())), credential);

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
        if (jwtStrategyEnabled) {
            return ((JwtTokenGenerator) jwtTokenGenerator).jwks();
        }
        logger.debug("Done getting well known jwk...");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token rotateToken(final TokenAuthenticationRequestDetails request) throws InvalidTokenException {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(request.getTokenType(), "request.tokenType cannot be null");
        Assert.notNull(request.getToken(), "request.token cannot be null");
        logger.trace("Rotating token for request:{}...", request);
        final LocalDateTime allowedTime = LocalDateTime.now().plusSeconds(allowedOffsetInSeconds);
        final Token token = this.getExistingValidToken(request.getToken(), request.getTokenType(), allowedTime);
        logger.debug("Done rotating token for request:{}", request);
        return token;
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
