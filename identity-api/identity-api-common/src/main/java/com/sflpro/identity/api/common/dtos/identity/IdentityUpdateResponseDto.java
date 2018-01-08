package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.core.datatypes.IdentityContactMethod;
import com.sflpro.identity.core.datatypes.IdentityStatus;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityUpdateResponseDto {

    private String id;

    private String description;

    private IdentityContactMethod contact_method;

    private IdentityStatus status;

    public IdentityUpdateResponseDto() {
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

    public IdentityContactMethod getContact_method() {
        return contact_method;
    }

    public void setContact_method(IdentityContactMethod contact_method) {
        this.contact_method = contact_method;
    }

    public IdentityStatus getStatus() {
        return status;
    }

    public void setStatus(IdentityStatus status) {
        this.status = status;
    }

}
