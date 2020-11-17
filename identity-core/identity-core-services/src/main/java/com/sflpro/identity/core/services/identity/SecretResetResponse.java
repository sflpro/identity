package com.sflpro.identity.core.services.identity;

/**
 * Company: SFL LLC
 * Created on 17/11/2020
 *
 * @author Norik Aslanyan
 */
public class SecretResetResponse {

    private Long notificationId;

    public SecretResetResponse(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}
