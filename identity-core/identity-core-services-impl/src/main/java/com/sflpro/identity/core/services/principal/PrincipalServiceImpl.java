package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.PrincipalStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.db.repositories.PrincipalRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.auth.AuthenticationServiceException;
import com.sflpro.identity.core.services.identity.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class PrincipalServiceImpl implements PrincipalService {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalServiceImpl.class);

    @Autowired
    private PrincipalRepository principalRepository;

    @Autowired
    private IdentityService identityService;

    @Override
    public Principal get(final PrincipalType type, final String name) {
        return principalRepository.findByDeletedIsNullAndNameAndPrincipalType(name, type)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Principal with name:%s not found", name)));
    }

    @Override
    @Transactional
    public List<Principal> update(final String identityId, final PrincipalUpdateRequest updateRequest) throws AuthenticationServiceException {
        logger.debug("Updating principal by identity id {}", identityId);
        Identity identity = identityService.get(identityId);

        identityService.chkSecretCorrectAndIdentityActive(identity, updateRequest.getSecret());

        // Check that each type has only one main contact
        updateRequest.getUpdateDetailsRequests().stream()
                .filter(p -> p.getPrincipalStatus() == PrincipalStatus.MAIN)
                .collect(Collectors.groupingBy(PrincipalUpdateDetailsRequest::getPrincipalType, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(p -> p.getValue() != 1)
                .findAny()
                .ifPresent(p -> {
                    if (p.getKey().isMainStatusRequired()) {
                        throw new IllegalArgumentException(String.format("Principal with MAIN status should exists and be no more then one for PrincipalType: %s", p.getKey()));
                    }
                });

        principalRepository.deleteAllByIdentity(identity);
        List<Principal> result = updateRequest.getUpdateDetailsRequests().stream()
                .map(principalUpdateDetailsRequest -> insert(identity, principalUpdateDetailsRequest))
                .collect(Collectors.toList());
        logger.trace("Complete updating principal by identity id {}.", identityId);
        return result;
    }

    private Principal insert(final Identity identity, final PrincipalUpdateDetailsRequest updateRequest) {
        Assert.notNull(identity, "identity cannot be null");
        Assert.notNull(updateRequest, "updateRequest cannot be null");
        Principal newInstance = new Principal();
        newInstance.setName(updateRequest.getPrincipalName());
        newInstance.setPrincipalType(updateRequest.getPrincipalType());
        newInstance.setPrincipalStatus(updateRequest.getPrincipalStatus());
        newInstance.setIdentity(identity);
        newInstance.setType(CredentialType.PRINCIPAL);
        return principalRepository.save(newInstance);
    }
}
