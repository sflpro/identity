package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Company: SFL LLC
 * Created on 7/18/2018
 *
 * @author Taron Petrosyan
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("select r " +
            "from Resource r " +
            "where (:type is null or r.type = :type) " +
            "and (:identifier is null or r.identifier = :identifier) " +
            "and r.deleted is null " +
            "order by r.type, r.identifier")
    List<Resource> search(@Param("type") String type, @Param("identifier") String identifier);
}
