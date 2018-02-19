package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;

import javax.transaction.Transactional;

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
     * Creates new identity
     *
     * @param identityCreationRequest identity creation request model
     * @return id and details of the created identity
     */
    @Deprecated
    @Transactional
    Identity create(final IdentityCreationRequest identityCreationRequest);

    /**
     * Updates identity and principals
     *
     * @param updateRequest identity update request model
     * @return id and details of the created identity
     */
    @Transactional
    Identity update(final String identityId, final IdentityUpdateRequest updateRequest) throws AuthenticationServiceException;

    /**
     * Request for secret reset, sending secret request token by other channel to the identity
     *
     * @param resetRequest identity identifier details
     */
    @Transactional
    void requestSecretReset(RequestSecretResetRequest resetRequest);

    /**
     * Reset secret
     *
     * @param secretReset token and secret to be reseted
     */
    @Transactional
    void secretReset(SecretResetRequest secretReset);

    /**
     * Check if the identity status is ACTIVE
     *
     * @param identity identity
     * @return true if identity status is ACTIVE, false for other statuses
     */
    boolean isIdentityActive(Identity identity);
}
