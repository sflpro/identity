package com.sflpro.identity.core.services.principal;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.PrincipalStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.db.repositories.PrincipalRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

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
    public Principal get(final PrincipalType type, final String name) {
        return principalRepository.findByDeletedIsNullAndNameAndPrincipalType(name, type)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Principal with name:%s not found", name)));
    }

    @Override
    @Transactional
    public void upsert(Identity identity, List<PrincipalUpdateRequest> updateRequest) {
        updateRequest.forEach(principalUpdateRequest -> {
            if (principalUpdateRequest.getPrincipalStatus() == PrincipalStatus.MAIN) {
                setStatusSecondaryByType(identity, principalUpdateRequest.getPrincipalType());
            }
        });
        updateRequest.forEach(principalUpdateRequest -> upsert(identity, principalUpdateRequest));
    }

    private Principal upsert(final Identity identity, final PrincipalUpdateRequest updateRequest) {
        Assert.notNull(identity, "identity cannot be null");
        Assert.notNull(updateRequest, "updateRequest cannot be null");
        try {
            Principal principal = get(updateRequest.getPrincipalType(), updateRequest.getPrincipalName());
            if (!principal.getIdentity().equals(identity)) {
                throw new PrincipalNameBusyException(updateRequest.getPrincipalType(), updateRequest.getPrincipalName());
            }
            principal.setName(updateRequest.getPrincipalName());
            principal.setPrincipalType(updateRequest.getPrincipalType());
            principal.setPrincipalStatus(updateRequest.getPrincipalStatus());
            return principalRepository.save(principal);
        } catch (ResourceNotFoundException e) {
            Principal newInstance = new Principal();
            newInstance.setName(updateRequest.getPrincipalName());
            newInstance.setPrincipalType(updateRequest.getPrincipalType());
            newInstance.setPrincipalStatus(updateRequest.getPrincipalStatus());
            newInstance.setIdentity(identity);
            newInstance.setType(CredentialType.PRINCIPAL);
            return principalRepository.save(newInstance);
        }
    }

    /**
     * If there are any MAIN principal types for the identity, change them all to secondary, before applying new MAIN principal     *
     * @param identity identity
     */
    private void setStatusSecondaryByType(final Identity identity, final PrincipalType principalType) {
        identity.getPrincipals().forEach(p -> {
            Principal changeStatus = (Principal) p;
            if (changeStatus.getPrincipalType() == principalType
                    && changeStatus.getPrincipalStatus() == PrincipalStatus.MAIN) {
                changeStatus.setPrincipalStatus(PrincipalStatus.SECONDARY);
                principalRepository.save(changeStatus);
            }
        });
    }
}
