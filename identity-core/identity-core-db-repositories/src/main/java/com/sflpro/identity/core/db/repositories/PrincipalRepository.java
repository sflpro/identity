package com.sflpro.identity.core.db.repositories;

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

    Optional<Principal> findByDeletedIsNullAndName(String name);

}
