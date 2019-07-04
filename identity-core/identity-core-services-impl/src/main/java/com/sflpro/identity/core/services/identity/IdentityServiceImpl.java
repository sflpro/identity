package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.datatypes.IdentityContactMethod;
import com.sflpro.identity.core.datatypes.IdentityStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.*;
import com.sflpro.identity.core.db.repositories.IdentityRepository;
import com.sflpro.identity.core.db.repositories.IdentityResourceRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.auth.InvalidCredentialsException;
import com.sflpro.identity.core.services.auth.SecretHashHelper;
import com.sflpro.identity.core.services.credential.CredentialService;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
import com.sflpro.identity.core.services.notification.NotificationCommunicationService;
import com.sflpro.identity.core.services.notification.SecretResetNotificationRequest;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.identity.core.services.resource.ResourceCreationRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import com.sflpro.identity.core.services.token.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class IdentityServiceImpl implements IdentityService {

    private static final Logger logger = LoggerFactory.getLogger(IdentityServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PrincipalService principalService;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private SecretHashHelper secretHashHelper;

    @Autowired
    private NotificationCommunicationService notificationCommunicationService;

    @Autowired
    private IdentityResourceRepository identityResourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CredentialService credentialService;

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

        identity.setContactMethod(updateRequest.getContactMethod());

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

        SecretResetNotificationRequest notificationRequest = new SecretResetNotificationRequest(resetRequest.getEmail(),
                resetRequest.getEmailTemplateName(),
                Map.of("RESET_TOKEN", token.getValue(), "REDIRECT_URI", resetRequest.getRedirectUri()));

        notificationCommunicationService.sendSecretResetEmail(notificationRequest);

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
            throw new InvalidCredentialsException();
        } else if (identity.getStatus() != IdentityStatus.ACTIVE) {
            logger.debug("Authenticating identity {} is Inactive.", identity);
            throw new InactiveIdentityException(identity);
        }
        return true;
    }

    @Override
    public boolean isIdentityActive(Identity identity) {
        return Objects.isNull(identity.getDeleted()) && identity.getStatus() == IdentityStatus.ACTIVE;
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
        if (!StringUtils.isEmpty(addRequest.getCreatorId()) && identityById.isPresent()) {
            identity.setCreatorId(identityById.get());
        }
        final Identity createdIdentity = identityRepository.save(identity);
        logger.trace("Complete adding identity - {}", createdIdentity);
        return createdIdentity;
    }

    @Override
    @Transactional
    public void delete(String id) {
        Identity identity = get(id);
        final LocalDateTime now = LocalDateTime.now();
        identity.setDeleted(now);
        identity.setStatus(IdentityStatus.DISABLED);
        credentialService.delete(identity.getId());
        logger.debug("Deleting identity Identity:'{}'.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Identity> list(long resourceId) {
        List<Identity> identities = identityResourceRepository.findIdentities(resourceId);
        logger.debug("Found {} identities for resource:'{}'", identities.size(), resourceId);
        return identities;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Resource> updateIdentityResources(IdentityResourceUpdateRequest updateRequest) {
        Assert.notNull(updateRequest, "updateRequest cannot be null");
        Assert.notNull(updateRequest.getIdentityId(), "updateRequest.identity id cannot be null");
        Assert.notNull(updateRequest.getResourceRequests(), "updateRequest.resourceRequests cannot be null");
        logger.debug("Updating resources of identity: {}", updateRequest);
        Identity identity = get(updateRequest.getIdentityId());

        identityResourceRepository.deleteAllByIdentityId(identity.getId());
        entityManager.flush();
        List<Resource> result = updateRequest.getResourceRequests().stream()
                .map(r -> Optional.ofNullable(resourceService.get(r.getType(), r.getIdentifier()))
                        .orElseGet(() -> {
                            ResourceCreationRequest creationRequest = new ResourceCreationRequest();
                            creationRequest.setType(r.getType());
                            creationRequest.setIdentifier(r.getIdentifier());
                            return resourceService.create(creationRequest);
                        }))
                .map(r -> insert(identity, r))
                .map(IdentityResource::getResource)
                .collect(Collectors.toList());
        logger.trace("Complete updating resources of identity: {}.", identity);
        return result;
    }

    private IdentityResource insert(final Identity identity, final Resource resource) {
        Assert.notNull(identity, "identity cannot be null");
        Assert.notNull(resource, "resource cannot be null");
        IdentityResource identityResource = new IdentityResource();
        identityResource.setResource(resource);
        identityResource.setIdentity(identity);
        return identityResourceRepository.save(identityResource);
    }

}
