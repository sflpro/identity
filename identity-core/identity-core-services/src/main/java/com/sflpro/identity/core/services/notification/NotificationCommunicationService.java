package com.sflpro.identity.core.services.notification;

import com.sflpro.identity.core.db.entities.Token;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 08/01/2018
 *
 * @author Davit Harutyunyan
 */
public interface NotificationCommunicationService {

  /**
   * Creates new identity
   *
   * @param email email
   * @param token secret reset token
   */
  void sendSecretResetEmail(@NotNull final String email, @NotNull final Token token);
}
