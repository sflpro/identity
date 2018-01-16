package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.identity.reset.SecretResetRequest;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface IdentityService {

    /**
     * Creates new identity
     *
     * @param identityCreationRequest identity creation request model
     * @return id and details of the created identity
     */
    Identity create(final IdentityCreationRequest identityCreationRequest);

    /**
     * Request for secret reset, sending secret request token by other channel to the identity
     *
     * @param resetRequest identity identifier details
     */
    void requestSecretReset(RequestSecretResetRequest resetRequest);

    /**
     * Reset secret
     *
     * @param secretReset token and secret to be reseted
     */
    void secretReset(SecretResetRequest secretReset);

    /**
     * Check if the identity status is ACTIVE
     *
     * @param identity identity
     * @return true if identity status is ACTIVE, false for other statuses
     */
    boolean isIdentityActive(Identity identity);
}
