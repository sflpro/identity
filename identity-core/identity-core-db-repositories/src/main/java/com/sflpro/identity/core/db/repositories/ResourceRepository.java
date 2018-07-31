package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Company: SFL LLC
 * Created on 7/18/2018
 *
 * @author Taron Petrosyan
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findFirstByDeletedIsNullAndTypeAndIdentifier(String type, String identifier);
}
