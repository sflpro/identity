package com.sflpro.identity.api.common.dtos.identity.reset;

import com.sflpro.identity.api.common.dtos.ApiResponseDto;

/**
 * Company: SFL LLC
 * Created on 17/11/2020
 *
 * @author Norik Aslanyan
 */
public class SecretResetResponseDto  extends ApiResponseDto {

    private Long notificationId;

    public SecretResetResponseDto(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}
