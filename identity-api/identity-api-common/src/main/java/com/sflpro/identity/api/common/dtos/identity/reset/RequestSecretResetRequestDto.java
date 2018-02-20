package com.sflpro.identity.api.common.dtos.identity.reset;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class RequestSecretResetRequestDto {

    @NotEmpty
    private String email;

    @NotNull
    private Integer expiresInHours;

    public RequestSecretResetRequestDto() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getExpiresInHours() {
        return expiresInHours;
    }

    public void setExpiresInHours(Integer expiresInHours) {
        this.expiresInHours = expiresInHours;
    }
}
