package com.sflpro.identity.core.db.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Company: SFL LLC
 * Created on 6/22/20
 *
 * @author Manuk Gharslyan
 */
@Entity
@Table(name = "identity_resource_role",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_identity_role", columnNames = {"identity_id", "role_id"}),
                @UniqueConstraint(name = "uk_identity_role_deleted", columnNames = {"identity_id", "role_id", "deleted"}),
                @UniqueConstraint(name = "uk_identity_resource_role", columnNames = {"identity_id", "resource_id", "role_id"}),
                @UniqueConstraint(name = "uk_identity_resource_role_deleted", columnNames = {"identity_id", "resource_id", "role_id", "deleted"})
        }
)
public class IdentityResourceRole {

    @Id
    @SequenceGenerator(name = "seq_identity_resource_role", sequenceName = "seq_identity_resource_role")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_resource_role")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "identity_id", nullable = false)
    private String identityId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @Column(name = "deleted")
    private LocalDateTime deleted;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        updated = created;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(final String identityId) {
        this.identityId = identityId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(final Long resourceId) {
        this.resourceId = resourceId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(final LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(final LocalDateTime deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof IdentityResourceRole)) return false;

        final IdentityResourceRole that = (IdentityResourceRole) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(identityId, that.identityId)
                .append(roleId, that.roleId)
                .append(resourceId, that.resourceId)
                .append(created, that.created)
                .append(updated, that.updated)
                .append(deleted, that.deleted)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(identityId)
                .append(roleId)
                .append(resourceId)
                .append(created)
                .append(updated)
                .append(deleted)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("id", id)
                .append("identityId", identityId)
                .append("roleId", roleId)
                .append("resourceId", resourceId)
                .append("created", created)
                .append("updated", updated)
                .append("deleted", deleted)
                .toString();
    }
}