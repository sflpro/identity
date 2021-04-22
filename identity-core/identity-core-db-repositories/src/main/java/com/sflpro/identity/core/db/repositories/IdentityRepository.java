package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface IdentityRepository extends JpaRepository<Identity, String> {

    Optional<Identity> findByDeletedIsNullAndId(final String id);

    Set<Identity> findAllByDeletedIsNullAndIdIn(Set<String> ids);
}
