package com.sflpro.identity.core.services.token;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 10/06/2020
 *
 * @author Davit Harutyunyan
 */
public class TokenGenerationRequest {

    private String identityId;
    private Long expiresIn;
    private Set<String> roles;
    private Set<String> permissions;
    private Long resourceId;
    private Map<String, List<String>> resources;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public Map<String, List<String>> getResources() {
        return resources;
    }

    public void setResources(Map<String, List<String>> resources) {
        this.resources = resources;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(final Set<String> permissions) {
        this.permissions = permissions;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(final Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TokenGenerationRequest that = (TokenGenerationRequest) o;

        return new EqualsBuilder()
                .append(identityId, that.identityId)
                .append(expiresIn, that.expiresIn)
                .append(roles, that.roles)
                .append(permissions, that.permissions)
                .append(resourceId, that.resourceId)
                .append(resources, that.resources)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(identityId)
                .append(expiresIn)
                .append(roles)
                .append(permissions)
                .append(resourceId)
                .append(resources)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identityId", identityId)
                .append("expiresIn", expiresIn)
                .append("roles", roles)
                .append("permissions", permissions)
                .append("resourceId", resourceId)
                .append("resources", resources)
                .toString();
    }
}
