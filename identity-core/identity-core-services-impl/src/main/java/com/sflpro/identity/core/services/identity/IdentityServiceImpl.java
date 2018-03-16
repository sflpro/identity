package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.datatypes.IdentityContactMethod;
import com.sflpro.identity.core.datatypes.IdentityStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.db.repositories.IdentityRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.auth.SecretHashHelper;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
import com.sflpro.identity.core.services.notification.NotificationCommunicationService;
import com.sflpro.identity.core.services.permission.PermissionService;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.identity.core.services.token.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

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
    private PermissionService permissionService;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private SecretHashHelper secretHashHelper;

    @Autowired
    private NotificationCommunicationService notificationCommunicationService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Identity get(final String identityId) {
        Assert.notNull(identityId, "identityId can not be null.");

        logger.debug("Getting identity by id  {}", identityId);

        Identity identity = identityRepository.findByDeletedIsNullAndId(identityId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Identity with id: %s not found", identityId)));
        Assert.notNull(identity, "identity can not be null.");
        logger.trace("Complete getting identity by id {}.", identity);
        return identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Identity update(@NotNull final String identityId, @NotNull final IdentityUpdateRequest updateRequest) throws AuthenticationServiceException {
        Assert.notNull(identityId, "identityId cannot be null");
        Assert.notNull(updateRequest, "updateRequest cannot be null");
        logger.debug("Updating identity by id {}", identityId);
        Identity identity = get(identityId);

        identity.setDescription(updateRequest.getDescription());
        // Change secret
        chkSecretCorrectAndIdentityActive(identity, updateRequest.getSecret());
        changeSecret(identity, updateRequest.getNewSecret());

        identity.setContactMethod(IdentityContactMethod.valueOf(updateRequest.getContactMethod()));

        identityRepository.save(identity);

        logger.trace("Complete updating identity by id {}.", identityId);
        return identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void requestSecretReset(RequestSecretResetRequest resetRequest) {
        Assert.notNull(resetRequest.getEmail(), "request.id can not be null.");

        logger.debug("Reset password requested for user {}", resetRequest.getEmail());

        Credential credential = principalService.get(PrincipalType.MAIL, resetRequest.getEmail());
        Identity identity = credential.getIdentity();

        Assert.notNull(identity, "identity can not be null.");

        Token token = tokenService.createNewToken(
                new TokenRequest(TokenType.SECRET_RESET, resetRequest.getExpiresInHours()), credential
        );

        // TODO Send email with reset token
        notificationCommunicationService.sendSecretResetEmail(resetRequest.getEmail(), token);

        logger.debug("Email sent to User {} for password reset.", identity);
    }

    /**
     * {@inheritDoc}
     */
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
            tokenService.invalidateToken(
                    new TokenInvalidationRequest(
                            token.getTokenType(),
                            token.getValue()
                    )
            );
        } catch (TokenServiceException e) {
            throw new IdentityServiceException(String.format("Could not demark the token: %s as used", secretReset.getToken()), e);
        }

        logger.debug("Secret was successfully reset from identity {}", identity.getId());
    }

    private Identity changeSecret(final Identity identity, final String newSecret) {
        identity.setSecret(secretHashHelper.hashSecret(newSecret));
        return identityRepository.save(identity);
    }

    @Override
    public boolean chkSecretCorrectAndIdentityActive(final Identity identity, final String secret) throws AuthenticationServiceException {
        if (!secretHashHelper.isSecretCorrect(secret, identity.getSecret())) {
            logger.debug("Invalid secret was supplied for identity {}.", identity);
            throw new AuthenticationServiceException("Invalid credentials");
        } else if (identity.getStatus() != IdentityStatus.ACTIVE) {
            logger.debug("Authenticating identity {} is Inactive.", identity);
            throw new InactiveIdentityException(identity);
        }
        return true;
    }

    @Override
    public boolean isIdentityActive(Identity identity) {
        return identity.getStatus() == IdentityStatus.ACTIVE;
    }

    @Override
    public Identity add(IdentityCreationRequest addRequest) {
        Assert.notNull(addRequest, "addRequest cannot be null");
        logger.debug("Creating identity  {}", addRequest);
        final Identity identity = new Identity();
        identity.setContactMethod(IdentityContactMethod.valueOf(addRequest.getContactMethod()));
        identity.setSecret(secretHashHelper.hashSecret(addRequest.getSecret()));
        identity.setDescription(addRequest.getDescription());
        identity.setStatus(IdentityStatus.ACTIVE);
        if (!addRequest.getStatus().isEmpty()) {
            identity.setStatus(IdentityStatus.valueOf(addRequest.getStatus()));
        }
        final Optional<Identity> identityById = identityRepository.findByDeletedIsNullAndId(addRequest.getCreatorId());
        if (!addRequest.getCreatorId().isEmpty() && identityById.isPresent()) {
            identity.setCreatorId(identityById.get());
        }
        final Identity createdIdentity = identityRepository.save(identity);
        logger.trace("Complete adding identity - {}", createdIdentity );
        return createdIdentity;
    }

    @Override
    @Transactional
    public void delete(String id) {
        Identity identity = get(id);
        final LocalDateTime now = LocalDateTime.now();
        identity.setDeleted(now);
        identityRepository.save(identity);
        logger.debug("Deleting identity Identity:'{}'.", id);
    }
}
