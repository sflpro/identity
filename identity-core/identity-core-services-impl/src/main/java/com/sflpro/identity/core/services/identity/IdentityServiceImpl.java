package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.datatypes.*;
import com.sflpro.identity.core.db.entities.*;
import com.sflpro.identity.core.db.repositories.IdentityRepository;
import com.sflpro.identity.core.db.repositories.IdentityResourceRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.auth.InvalidCredentialsException;
import com.sflpro.identity.core.services.auth.SecretHashHelper;
import com.sflpro.identity.core.services.credential.CredentialCreation;
import com.sflpro.identity.core.services.credential.CredentialService;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
import com.sflpro.identity.core.services.identity.resource.role.IdentityResourceRoleCreationRequest;
import com.sflpro.identity.core.services.identity.resource.role.IdentityResourceRoleService;
import com.sflpro.identity.core.services.notification.NotificationCommunicationService;
import com.sflpro.identity.core.services.notification.SecretResetNotificationRequest;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.identity.core.services.resource.ResourceCreationRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import com.sflpro.identity.core.services.role.RoleService;
import com.sflpro.identity.core.services.token.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;
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

    @Value("${email.token.key}")
    private String emailTokenKey;

    @Value("${email.redirect.uri.key}")
    private String emailRedirectUriKey;

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
    private RoleService roleService;

    @Autowired
    private IdentityResourceRoleService identityResourceRoleService;

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
        final Identity identity = get(identityId);
        if (updateRequest.getDescription() != null) {
            identity.setDescription(updateRequest.getDescription());
        }
        // Change secret
        if (updateRequest.getSecret() != null) {
            chkSecretCorrectAndIdentityActive(identity, updateRequest.getSecret());
            changeSecret(identity, updateRequest.getNewSecret());
        }

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
    public SecretResetResponse requestSecretReset(RequestSecretResetRequest resetRequest) {
        Assert.notNull(resetRequest.getEmail(), "request.id can not be null.");

        logger.debug("Reset password requested for user {}", resetRequest.getEmail());

        Credential credential = principalService.get(PrincipalType.MAIL, resetRequest.getEmail());
        Identity identity = credential.getIdentity();

        Assert.notNull(identity, "identity can not be null.");
        Token token = tokenService.createNewToken(
                new TokenRequest(TokenType.SECRET_RESET, resetRequest.getExpiresInHours(), resetRequest.getResourceRequest()), credential);

        SecretResetNotificationRequest notificationRequest = new SecretResetNotificationRequest(resetRequest.getEmail(),
                resetRequest.getEmailTemplateName(),
                Map.of(emailTokenKey, token.getValue(), emailRedirectUriKey, resetRequest.getRedirectUri()));

        final Long notificationId = notificationCommunicationService.sendSecretResetEmail(notificationRequest);

        logger.debug("Email sent to User {} for password reset.", identity);
        return new SecretResetResponse(notificationId);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityResponse add(final IdentityCreationRequest addRequest) {
        Assert.notNull(addRequest, "addRequest cannot be null");
        logger.debug("Creating identity  {}", addRequest);

        // create identity
        final Identity identity = new Identity();
        identity.setContactMethod(IdentityContactMethod.valueOf(addRequest.getContactMethod()));
        if (StringUtils.isNotBlank(addRequest.getSecret())) {
            identity.setSecret(secretHashHelper.hashSecret(addRequest.getSecret()));
        }
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
        entityManager.flush();

        // add roles
        this.setRoles(identity.getId(), addRequest.getRoles());

        // add tokens
        final CredentialCreation credentialCreation = new CredentialCreation();
        credentialCreation.setCredentialType(CredentialType.DEFAULT);
        credentialCreation.setDetails("No credential, default token");
        final Credential credential = credentialService.store(identity, credentialCreation);
        final Set<Token> tokens = new HashSet<>();
        for (TokenRequest tokenRequest : addRequest.getTokenRequests()) {
            tokens.add(tokenService.createNewToken(new TokenRequest(TokenType.REFRESH, tokenRequest.getRoleResource()), credential));
        }
        final IdentityResponse identityResponse = new IdentityResponse(createdIdentity.getId(),
                createdIdentity.getDescription(), createdIdentity.getContactMethod(),
                createdIdentity.getStatus(), tokens);
        logger.trace("Complete adding identity - {} with result - {}", createdIdentity, identityResponse);
        return identityResponse;
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void setRoles(final String identityId, final Set<RoleAdditionRequest> additionRequests) {
        Assert.notNull(additionRequests, "additionRequest cannot be null");
        Assert.notEmpty(additionRequests, "additionRequest cannot be empty");
        logger.trace("Adding roles for identity:{}...", identityId);
        // delete roles
        for (RoleAdditionRequest request : additionRequests) {
            if (request.getResource() != null) {
                final Resource resource = resourceService.get(request.getResource().getType(), request.getResource().getIdentifier());
                identityResourceRoleService.deleteByIdentityAndResource(identityId, resource.getId());
            } else {
                identityResourceRoleService.deleteByIdentityAndResource(identityId, null);
            }
        }
        // add roles
        for (RoleAdditionRequest request : additionRequests) {
            final IdentityResourceRoleCreationRequest identityResourceRoleCreationRequest;
            final Role role = roleService.getByName(request.getName());
            if (request.getResource() != null) {
                final Resource resource = resourceService.get(request.getResource().getType(), request.getResource().getIdentifier());
                identityResourceRoleCreationRequest = new IdentityResourceRoleCreationRequest(identityId, role.getId(), resource.getId());
            } else {
                identityResourceRoleCreationRequest = new IdentityResourceRoleCreationRequest(identityId, role.getId());
            }
            identityResourceRoleService.create(identityResourceRoleCreationRequest);
        }
        logger.debug("Done adding roles for identity:{}", identityId);
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

    @Transactional(readOnly = true)
    @Override
    public Set<Identity> getAll(final Set<String> ids) {
        Assert.notEmpty(ids, "ids can't be empty");
        logger.trace("Getting identities for ids:{}", ids);
        final Set<Identity> identities = identityRepository.findAllByDeletedIsNullAndIdIn(ids);
        logger.debug("Done getting identities for ids:{}", ids);
        return identities;
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
