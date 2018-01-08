package com.sflpro.identity.api.common.dtos.role;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class RoleUpdateResponseDto extends AbstractApiResponse {

    private Long id;

    public RoleUpdateResponseDto() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
