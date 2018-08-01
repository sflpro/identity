package com.sflpro.identity.core.services.resource;

/**
 * Company: SFL LLC
 * Created on 7/18/2018
 *
 * @author Taron Petrosyan
 */
public class ResourceUpdateRequest {

    private Long id;

    private String type;

    private String identifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
