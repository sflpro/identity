package com.sflpro.identity.api.common.dtos.identity.reset;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class RequestSecretResetRequestDto {

    @NotEmpty
    private String email;

    public RequestSecretResetRequestDto() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
