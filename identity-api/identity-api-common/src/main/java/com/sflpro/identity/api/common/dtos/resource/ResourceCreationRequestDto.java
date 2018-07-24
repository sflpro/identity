package com.sflpro.identity.api.common.dtos.resource;

import javax.validation.constraints.NotBlank;

/**
 * Company: SFL LLC
 * Created on 7/18/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceCreationRequestDto {

    @NotBlank
    private String type;

    @NotBlank
    private String identifier;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
