package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.db.repositories.PrincipalRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class PrincipalServiceImpl implements PrincipalService {

    @Autowired
    private PrincipalRepository principalRepository;

    @Override
    public Principal get(final String name, final PrincipalType type) throws ResourceNotFoundException {
        return principalRepository.findByDeletedIsNullAndNameAndPrincipalType(name, type)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Principal with name:%s not found", name)));
    }

    @Override
    public Principal getByCredentialAndType(Credential credential, PrincipalType principalType) {
        Assert.notNull(credential, "credential cannot be null");
        Assert.notNull(credential.getId(), "credential id cannot be null");
        return principalRepository.findByDeletedIsNullAndIdAndPrincipalType(credential.getId(), principalType)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Principal with credential:%s not found", credential.getId())));
    }

    @Override
    public Principal getByIdentityAndType(Identity identity, PrincipalType principalType) {
        Assert.notNull(identity, "identity cannot be null");
        Assert.notNull(identity.getId(), "identity id cannot be null");
        return principalRepository.findByDeletedIsNullAndIdentityAndPrincipalType(identity, principalType)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Principal with identity:%s not found", identity.getId())));
    }
}
