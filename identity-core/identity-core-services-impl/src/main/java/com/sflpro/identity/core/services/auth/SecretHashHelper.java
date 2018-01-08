package com.sflpro.identity.core.services.auth;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface SecretHashHelper {

    String hashSecret(String plainPassword);

    Boolean isSecretCorrect(String plainPassword, String hashedPassword);
}
