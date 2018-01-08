package com.sflpro.identity.core.services.auth.impl;

import com.sflpro.identity.core.services.auth.SecretHashHelper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Component
public class SecretHashHelperImpl implements SecretHashHelper {

    private static final int LOG_ROUNDS = 12;

    @Override
    public String hashSecret(String plainPassword) {
        String salt = BCrypt.gensalt(LOG_ROUNDS);
        return BCrypt.hashpw(plainPassword, salt);
    }

    @Override
    public Boolean isSecretCorrect(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
