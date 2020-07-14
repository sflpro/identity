package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Resource;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface IdentityService {

    /**
     * Gets new identity
     *
     * @param identityId identity's id
     * @return details of the identity
     */
    Identity get(final String identityId);

    /**
     * Updates identity and principals
     *
     * @param identityId the ide of identity
     * @param updateRequest identity update request model
     * @return id and details of the created identity
     */
    Identity update(final String identityId, final IdentityUpdateRequest updateRequest) throws AuthenticationServiceException;

    /**
     * Request for secret reset, sending secret request token by other channel to the identity
     *
     * @param resetRequest identity identifier details
     */
    void requestSecretReset(final RequestSecretResetRequest resetRequest);

    /**
     * Reset secret
     *
     * @param secretReset token and secret to be reseted
     */
    void secretReset(final SecretResetRequest secretReset);

    /**
     * Check if the secret is correct and identity status is ACTIVE
     *
     * @param identity identity
     * @param secret secret of identity
     * @return true if identity status is ACTIVE, false for other statuses
     * @throws AuthenticationServiceException authentication service exception
     */
    boolean chkSecretCorrectAndIdentityActive(final Identity identity, final String secret) throws AuthenticationServiceException;

    /**
     * Check if the identity status is ACTIVE
     *
     * @param identity identity
     * @return true if identity status is ACTIVE, false for other statuses
     */
    boolean isIdentityActive(Identity identity);

    /**
     * Adds identity
     *
     * @param addRequest identity update request model
     * @return id and details of the created identity
     */
    IdentityResponse add(final IdentityCreationRequest addRequest);

    /**
     * Deletes identity for provided id
     * @param id
     */
    void delete(String id);

    /**
     * Add roles to identity for provided request
     *
     * @param identityId
     * @param additionRequest
     * @return
     */
    @Transactional
    void setRoles(String identityId, Set<RoleAdditionRequest> additionRequest);

    /**
     *  Lists all identities for specified resource
     * @param resourceId id of resource which identities should be retrieved
     * @return list of resource's identities
     */
    List<Identity> list(final long resourceId);

    /**
     *  Update resources of specified identity
     * @param updateRequest identity and resources info
     * @return updated list of identity's resources
     */
    List<Resource> updateIdentityResources(IdentityResourceUpdateRequest updateRequest);
}
