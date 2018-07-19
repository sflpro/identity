package com.sflpro.identity.api.common.dtos.resource;

/**
 * Company: SFL LLC
 * Created on 7/18/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceUpdateRequestDto {

    private String type;

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
