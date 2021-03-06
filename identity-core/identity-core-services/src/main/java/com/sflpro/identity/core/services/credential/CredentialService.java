package com.sflpro.identity.core.services.credential;

import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface CredentialService {

    /**
     * Gets Credential by id
     *
     * @param id the id of the entity
     * @return credential entity
     */
    Credential getCredentialById(String id);

    /**
     * Stores credential for provided identity
     *
     * @param identity
     * @param credentialCreationRequest
     * @return
     */
    @Transactional
    Credential store(Identity identity, CredentialCreation credentialCreationRequest);

    /**
     * Stores credential and identity
     *
     * @param identity identity of the credential
     * @param credentialCreations list of credential cretion details
     */
    void store(final Identity identity, final List<CredentialCreation> credentialCreations);

    /**
     *  Deletes all credentials of identity
     *
     * @param identityId  id of identity whose credentials should be deleted
     */
    void delete(final String identityId);

    /**
     * Updates failed attempts Credential
     *
     * @param credential credential need to update
     * @return updated credential entity
     */
    Credential updateFailedAttempts(Credential credential, int val);
}
