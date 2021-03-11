package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByDeletedIsNullAndId(final Long id);

    Optional<Role> findByNameAndDeletedIsNull(String name);

    Set<Role> findAllByDeletedIsNull();

}
