package com.sflpro.identity.core.services.identity.reset;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class RequestSecretResetRequest {

    @NotNull
    private String email;

    @NotNull
    private Integer expiresInHours;

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
