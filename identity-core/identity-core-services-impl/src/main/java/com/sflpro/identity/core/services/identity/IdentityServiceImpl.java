package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.datatypes.IdentityStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.db.repositories.IdentityRepository;
import com.sflpro.identity.core.services.auth.SecretHashHelper;
import com.sflpro.identity.core.services.credential.CredentialService;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
import com.sflpro.identity.core.services.notification.NotificationCommunicationService;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.identity.core.services.token.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class IdentityServiceImpl implements IdentityService {

    private static final Logger logger = LoggerFactory.getLogger(IdentityServiceImpl.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PrincipalService principalService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private SecretHashHelper secretHashHelper;

    @Autowired
    private NotificationCommunicationService notificationCommunicationService;

    @Override
    public Identity create(final IdentityCreationRequest identityCreationRequest) {
        Assert.notNull(identityCreationRequest, "Identity creation request cannot be null");
        Assert.notEmpty(identityCreationRequest.getCredentials(), "Credentials can not be null");
        logger.info("Creating identity '{}'", identityCreationRequest.getDescription());

        // Creating identity
        Identity identity = new Identity();
        identity.setSecret(identityCreationRequest.getSecret());
        identity.setStatus(IdentityStatus.ACTIVE);
        identity.setDescription(identityCreationRequest.getDescription());
        identity = identityRepository.save(identity);

        // Storing credentials
        credentialService.store(identity, identityCreationRequest.getCredentials());

        return identity;
    }

    @Override
    @Transactional
    public void requestSecretReset(RequestSecretResetRequest resetRequest) {
        Assert.notNull(resetRequest.getEmail(), "request.id can not be null.");

        logger.debug("Reset password requested for user {}", resetRequest.getEmail());

        Credential credential = principalService.get(resetRequest.getEmail(), PrincipalType.MAIL);
        Identity identity = credential.getIdentity();

        Assert.notNull(identity, "identity can not be null.");

        Token token = tokenService.createNewToken(
                new TokenRequest(TokenType.SECRET_RESET, resetRequest.getExpiresInHours()), credential
        );

        // TODO Send email with reset token
        notificationCommunicationService.sendSecretResetEmail(resetRequest.getEmail(), token);

        logger.debug("Email sent to User {} for password reset.", identity);
    }

    @Override
    @Transactional
    public void secretReset(SecretResetRequest secretReset) {
        Assert.notNull(secretReset.getToken(), "request.tokenValue can not be null.");

        logger.debug("Reset secret, updating secret using token {}", secretReset.getToken());

        Token token;
        try {
            token = tokenService.getExistingToken(
                    new TokenExistenceCheckRequest(
                            TokenType.SECRET_RESET,
                            secretReset.getToken()
                    )
            );
        } catch (TokenServiceException e) {
            throw new IdentityServiceException(String.format("Could not validate the token: %s", secretReset.getToken()), e);
        }

        Identity identity = token.getIssuedBy().getIdentity();
        if (identity != null) {
            changeSecret(identity, secretReset.getSecret());
        } else {
            logger.error("Token Owner was not found!");

            throw new IllegalStateException("Token Owner can not be found.");
        }

        try {
            tokenService.demarkTokenAsUsed(
                    new TokenDemarcationAsUsedRequest(
                            token.getTokenType(),
                            token.getValue(),
                            identity.getId()
                    )
            );
        } catch (TokenServiceException e) {
            throw new IdentityServiceException(String.format("Could not demark the token: %s as used", secretReset.getToken()), e);
        }

        logger.debug("Secret was successfully reset from identity {}", identity.getId());
    }

    @Transactional
    protected Identity changeSecret(final Identity identity, final String newSecret) {
        identity.setSecret(secretHashHelper.hashSecret(newSecret));
        return identityRepository.save(identity);
    }

    @Override
    public boolean isIdentityActive(Identity identity) {
        return identity.getStatus() == IdentityStatus.ACTIVE;
    }
}
