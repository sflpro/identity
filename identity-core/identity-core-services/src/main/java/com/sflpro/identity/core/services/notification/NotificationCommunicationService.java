package com.sflpro.identity.core.services.notification;

import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;

import javax.validation.Valid;
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
   * @param resetRequest request
   * @param token secret reset token
   */
  void sendSecretResetEmail(@Valid final RequestSecretResetRequest resetRequest, @NotNull final Token token);
}
