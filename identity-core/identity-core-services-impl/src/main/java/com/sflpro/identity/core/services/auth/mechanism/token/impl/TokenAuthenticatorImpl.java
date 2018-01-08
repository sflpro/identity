package com.sflpro.identity.core.services.auth.mechanism.token.impl;

import com.sflpro.identity.core.datatypes.AuthenticationStatus;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.*;
import com.sflpro.identity.core.services.auth.impl.AbstractAuthenticatorImpl;
import com.sflpro.identity.core.services.auth.mechanism.token.TokenAuthenticationRequestDetails;
import com.sflpro.identity.core.services.auth.mechanism.token.TokenCredentialIdentifier;
import com.sflpro.identity.core.services.identity.IdentityService;
import com.sflpro.identity.core.services.identity.InactiveIdentityException;
import com.sflpro.identity.core.services.token.TokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Company: SFL LLC
 * Created on 01/12/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class TokenAuthenticatorImpl extends AbstractAuthenticatorImpl<Token, TokenCredentialIdentifier, TokenAuthenticationRequestDetails> {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticatorImpl.class);

    private final IdentityService identityService;

    @Autowired
    public TokenAuthenticatorImpl(IdentityService identityService) {
        super(CredentialType.TOKEN);
        this.identityService = identityService;
    }

    @Override
    protected AuthenticationResponse processAuthentication(@NotNull Token token, @NotNull TokenAuthenticationRequestDetails authenticationRequestDetails) throws AuthenticationServiceException {
        Assert.notNull(token, "credential cannot be null");
        Assert.notNull(authenticationRequestDetails, "request cannot be null.");
        Assert.notNull(authenticationRequestDetails.getTokenType(), "request.tokenType cannot be null.");
        Assert.notNull(authenticationRequestDetails.getToken(), "request.token cannot be null.");

        logger.debug("Attempting find identity with credential type{'{}'}.", authenticationRequestDetails.getCredentialType());

        final Identity identity = token.getIdentity();
        logger.trace("Found identity {}.", identity);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, identity);
        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(authenticationRequestDetails.getToken());
        } else if (!identityService.isIdentityActive(identity)) {
            throw new InactiveIdentityException(identity);
        }
        authenticationResponse.setStatus(AuthenticationStatus.AUTHENTICATED);
        return authenticationResponse;
    }

    @Override
    public Class<TokenAuthenticationRequestDetails> getAuthenticationRequestDetailsType() {
        return TokenAuthenticationRequestDetails.class;
    }
}
