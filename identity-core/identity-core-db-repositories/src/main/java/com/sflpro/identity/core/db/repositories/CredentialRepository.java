package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.Credential;
import org.springframework.data.repository.CrudRepository;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface CredentialRepository extends CrudRepository<Credential, String> {
}
