package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.IdentityResource;
import com.sflpro.identity.core.db.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 14/02/2018
 *
 * @author Davit Harutyunyan
 */
public interface IdentityResourceRepository extends JpaRepository<IdentityResource, String> {

    @Query("select r.resource " +
            "from IdentityResource r " +
            "where r.identity.id = :identityId " +
            "and r.resource.id = r.id " +
            "and (:resourceType is null or lower(r.resource.type) = :resourceType) " +
            "and (:resourceIdentifier is null or r.resource.identifier = :resourceIdentifier) " +
            "and r.resource.deleted is null " +
            "order by r.resource.type, r.resource.identifier")
    List<Resource> search(@Param("identityId") String identityId, @Param("resourceType") String resourceType, @Param("resourceIdentifier") String resourceIdentifier);
}
