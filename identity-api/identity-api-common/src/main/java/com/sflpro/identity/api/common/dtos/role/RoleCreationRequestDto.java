package com.sflpro.identity.api.common.dtos.role;

import javax.validation.constraints.NotEmpty;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class RoleCreationRequestDto {

    @NotEmpty
    private String name;

    public RoleCreationRequestDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
