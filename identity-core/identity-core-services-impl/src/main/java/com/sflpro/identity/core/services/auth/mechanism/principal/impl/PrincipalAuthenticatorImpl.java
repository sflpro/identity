package com.sflpro.identity.core.services.auth.mechanism.principal.impl;

import com.sflpro.identity.core.datatypes.AuthenticationStatus;
import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.services.auth.AuthenticationResponse;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.auth.impl.AbstractAuthenticatorImpl;
import com.sflpro.identity.core.services.auth.mechanism.principal.PrincipalAuthenticationRequestDetails;
import com.sflpro.identity.core.services.auth.mechanism.principal.PrincipalCredentialIdentifier;
import com.sflpro.identity.core.services.identity.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Company: SFL LLC
 * Created on 30/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class PrincipalAuthenticatorImpl extends AbstractAuthenticatorImpl<Principal, PrincipalCredentialIdentifier, PrincipalAuthenticationRequestDetails> {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalAuthenticatorImpl.class);

    @Autowired
    private IdentityService identityService;

    public PrincipalAuthenticatorImpl() {
        super(CredentialType.PRINCIPAL);
    }

    @Override
    protected AuthenticationResponse processAuthentication(final Principal principal, final PrincipalAuthenticationRequestDetails authenticationRequestDetails) throws AuthenticationServiceException {
        Assert.notNull(principal, "principal cannot be null");
        Assert.notNull(authenticationRequestDetails, "request cannot be null.");
        Assert.notNull(authenticationRequestDetails.getPrincipal(), "request.principal cannot be null.");
        Assert.notNull(authenticationRequestDetails.getSecret(), "request.secret cannot be null.");
        logger.debug("Attempting find identity with credential type{'{}'}.", authenticationRequestDetails.getCredentialType());
        final Identity identity = principal.getIdentity();
        logger.trace("Found identity {}.", identity);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(principal, identity);
        identityService.chkSecretCorrectAndIdentityActive(identity, authenticationRequestDetails.getSecret());
        logger.trace("Authenticating identity {}'s secret is correct.", identity);
        authenticationResponse.setStatus(AuthenticationStatus.AUTHENTICATED);
        return authenticationResponse;
    }

    @Override
    public Class<PrincipalAuthenticationRequestDetails> getAuthenticationRequestDetailsType() {
        return PrincipalAuthenticationRequestDetails.class;
    }
}
