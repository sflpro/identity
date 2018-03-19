package com.sflpro.identity.core.services.credential;

import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.repositories.CredentialRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public Credential updateFailedAttempts(Credential credential, int val) {
        credential.setFailedAttempts(val);
        return credentialRepository.save(credential);
    }

}
