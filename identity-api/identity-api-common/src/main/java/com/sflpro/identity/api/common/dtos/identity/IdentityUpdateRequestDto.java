package com.sflpro.identity.api.common.dtos.identity;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityUpdateRequestDto {

    @NotEmpty
    private String description;

    @NotEmpty
    private String secret;

    @NotEmpty
    private String newSecret;

    public IdentityUpdateRequestDto() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getNewSecret() {
        return newSecret;
    }

    public void setNewSecret(String newSecret) {
        this.newSecret = newSecret;
    }
}
