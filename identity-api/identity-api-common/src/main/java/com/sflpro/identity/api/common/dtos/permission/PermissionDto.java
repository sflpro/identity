package com.sflpro.identity.api.common.dtos.permission;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;
import com.sflpro.identity.core.datatypes.PermissionType;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class PermissionDto extends AbstractApiResponse {

    private Long id;

    private String name;

    private PermissionType type;

    public PermissionDto() {
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
