package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Identity;
import com.sflpro.identity.core.db.entities.Principal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface PrincipalRepository extends JpaRepository<Principal, String> {

    Optional<Principal> findByDeletedIsNullAndNameAndPrincipalType(String name, PrincipalType type);

    void deleteAllByIdentity(final Identity identity);
}
