package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.core.datatypes.IdentityContactMethod;
import com.sflpro.identity.core.datatypes.IdentityStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityDto {

    @NotEmpty
    private String id;

    @NotEmpty
    private String description;

    private IdentityContactMethod contactMethod;

    @NotNull
    private IdentityStatus status;

    public IdentityDto() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IdentityContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(IdentityContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    public IdentityStatus getStatus() {
        return status;
    }

    public void setStatus(IdentityStatus status) {
        this.status = status;
    }
}
