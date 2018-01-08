package com.sflpro.identity.core.services.permission;

import com.sflpro.identity.core.datatypes.PermissionType;

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

    private PermissionType type;

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

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }
}
