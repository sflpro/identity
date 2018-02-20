package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;
import com.sflpro.identity.api.common.dtos.principal.PrincipalDto;
import com.sflpro.identity.core.datatypes.IdentityContactMethod;
import com.sflpro.identity.core.datatypes.IdentityStatus;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityDto extends AbstractApiResponse {

    private String id;

    private String description;

    private IdentityContactMethod contactMethod;

    private IdentityStatus status;

    private Set<PrincipalDto> principals;

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

    public Set<PrincipalDto> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Set<PrincipalDto> principals) {
        this.principals = principals;
    }
}
