package com.sflpro.identity.core.services.permission;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public class PermissionRequest {

    private Long id;

    @NotEmpty
    private String name;

    private String type;

    public PermissionRequest() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
