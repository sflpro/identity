package com.sflpro.identity.core.services.notification;

import javax.validation.Valid;

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
     * @param notificationRequest request
     * @return  notification id
     */
    Long sendSecretResetEmail(@Valid final SecretResetNotificationRequest notificationRequest);
}
