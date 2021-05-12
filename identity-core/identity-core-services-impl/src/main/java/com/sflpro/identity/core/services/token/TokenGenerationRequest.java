package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.services.resource.ResourceRequest;
import com.sflpro.identity.core.services.token.metadata.MetadataPayload;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 10/06/2020
 *
 * @author Davit Harutyunyan
 */
public class TokenGenerationRequest {

    private String identityId;
    private Long expiresInSeconds;
    private Set<String> roles;
    private Set<String> permissions;
    private ResourceRequest resourceRole;
    private Map<String, List<String>> resources;
    private Set<MetadataPayload<?>> metadataPayloads = Collections.emptySet();

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

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(Long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
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

    public ResourceRequest getResourceRole() {
        return resourceRole;
    }

    public void setResourceRole(ResourceRequest resourceRole) {
        this.resourceRole = resourceRole;
    }

    public Set<MetadataPayload<?>> getMetadataPayloads() {
        return Collections.unmodifiableSet(metadataPayloads);
    }

    public void setMetadataPayloads(final Set<MetadataPayload<?>> metadataPayloads) {
        assertMetadataPayloads(metadataPayloads);
        this.metadataPayloads = metadataPayloads;
    }

    private void assertMetadataPayloads(final Set<MetadataPayload<?>> metadataPayloads) {
        Assert.notNull(metadataPayloads, "The metadataPayloads should not be null");
        if (metadataPayloads.size() != metadataPayloads.stream()
                .map(MetadataPayload::getKey)
                .collect(Collectors.toUnmodifiableSet())
                .size()) {
            throw new IllegalArgumentException("The metadata should not contain duplicate keys");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TokenGenerationRequest that = (TokenGenerationRequest) o;

        return new EqualsBuilder()
                .append(identityId, that.identityId)
                .append(expiresInSeconds, that.expiresInSeconds)
                .append(roles, that.roles)
                .append(permissions, that.permissions)
                .append(resourceRole, that.resourceRole)
                .append(resources, that.resources)
                .append(metadataPayloads, that.metadataPayloads)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(identityId)
                .append(expiresInSeconds)
                .append(roles)
                .append(permissions)
                .append(resourceRole)
                .append(resources)
                .append(metadataPayloads)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identityId", identityId)
                .append("expiresIn", expiresInSeconds)
                .append("roles", roles)
                .append("permissions", permissions)
                .append("resourceRequest", resourceRole)
                .append("resources", resources)
                .append("metadataPayloads", metadataPayloads)
                .toString();
    }
}
