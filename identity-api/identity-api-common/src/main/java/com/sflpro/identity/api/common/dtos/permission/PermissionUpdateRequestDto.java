package com.sflpro.identity.api.common.dtos.permission;

import com.sflpro.identity.core.datatypes.PermissionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class PermissionUpdateRequestDto {

    @NotEmpty
    private String name;

    @NotNull
    private PermissionType type;

    public PermissionUpdateRequestDto() {
        super();
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
