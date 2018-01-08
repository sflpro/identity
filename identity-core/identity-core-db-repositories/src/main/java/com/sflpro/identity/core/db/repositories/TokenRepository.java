package com.sflpro.identity.core.db.repositories;

import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.db.entities.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findByTokenTypeAndValue(String tokenType, String tokenValue);

    Iterable<Token> findAllByTokenTypeAndIssuedBy(String tokenType, Credential issuedBy);
}
