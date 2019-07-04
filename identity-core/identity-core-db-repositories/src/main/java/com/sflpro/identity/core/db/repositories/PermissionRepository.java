package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Permission findByDeletedIsNullAndId(final Long id);
}
