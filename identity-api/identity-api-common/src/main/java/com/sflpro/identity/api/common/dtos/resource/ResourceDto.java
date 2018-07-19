package com.sflpro.identity.api.common.dtos.resource;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;

/**
 * Company: SFL LLC
 * Created on 14/02/2018
 *
 * @author Davit Harutyunyan
 */
public class ResourceDto extends AbstractApiResponse {

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
