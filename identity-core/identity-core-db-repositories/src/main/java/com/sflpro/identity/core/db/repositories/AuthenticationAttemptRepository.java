package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.AuthenticationAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface AuthenticationAttemptRepository extends JpaRepository<AuthenticationAttempt, String> {
}
