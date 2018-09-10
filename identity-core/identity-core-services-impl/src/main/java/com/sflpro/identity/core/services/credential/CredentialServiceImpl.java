package com.sflpro.identity.core.services.credential;

import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.repositories.CredentialRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public Credential getCredentialById(String id) throws ResourceNotFoundException {
        return credentialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Credential Id:'%s' can not be found.", id)));
    }

    @Override
    public void store(final Identity identity, final List<CredentialCreation> credentialCreations) {
        credentialCreations.forEach(credentialCreation -> {
            final Credential credential = new Credential();
            credential.setIdentity(identity);
            credential.setType(credentialCreation.getCredentialType());
            credentialRepository.save(credential);
        });
    }

    @Override
    @Transactional
    public void delete(final String identityId) {
        Assert.notNull(identityId, "identity cannot be null");
        Set<Credential> credentials = credentialRepository.findAllByIdentityIdAndDeletedIsNull(identityId);
        credentials.forEach(c -> c.setDeleted(LocalDateTime.now()));
        credentialRepository.saveAll(credentials);
    }

    @Override
    @Transactional
    public Credential updateFailedAttempts(final Credential credential, final int val) {
        Assert.notNull(credential, "credential cannot be null");
        credential.setFailedAttempts(val);
        credential.setLastFailedAttempt(LocalDateTime.now());
        return credentialRepository.save(credential);
    }
}
