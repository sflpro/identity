package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.resource.ResourceRequestDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Company: SFL LLC
 * Created on 24/06/2020
 *
 * @author Davit Harutyunyan
 */
public class RoleAdditionRequestDto {

    private String name;

    private ResourceRequestDto resource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceRequestDto getResource() {
        return resource;
    }

    public void setResource(ResourceRequestDto resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RoleAdditionRequestDto that = (RoleAdditionRequestDto) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(resource, that.resource)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(resource)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("resource", resource)
                .toString();
    }
}
