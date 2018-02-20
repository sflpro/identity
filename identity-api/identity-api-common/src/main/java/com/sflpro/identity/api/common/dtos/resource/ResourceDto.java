package com.sflpro.identity.api.common.dtos.resource;

/**
 * Company: SFL LLC
 * Created on 14/02/2018
 *
 * @author Davit Harutyunyan
 */
public class ResourceDto {

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
