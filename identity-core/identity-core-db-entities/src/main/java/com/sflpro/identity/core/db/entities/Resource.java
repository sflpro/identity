package com.sflpro.identity.core.db.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 14/02/2018
 *
 * @author Davit Harutyunyan
 */
@Entity
@Table(
        name = "resource",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_resource_type_identifier", columnNames = {"type", "identifier", "deleted"})
        }
)
public class Resource {

    @Id
    @SequenceGenerator(name = "seq_resource", sequenceName = "seq_resource")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resource")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "identity_role",
            joinColumns = {@JoinColumn(name = "resource_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return new EqualsBuilder()
                .append(id, resource.id)
                .append(type, resource.type)
                .append(identifier, resource.identifier)
                .append(roles, resource.roles)
                .append(created, resource.created)
                .append(updated, resource.updated)
                .append(deleted, resource.deleted)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(identifier)
                .append(roles)
                .append(created)
                .append(updated)
                .append(deleted)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("type", type)
                .append("identifier", identifier)
                .append("roles", roles)
                .append("created", created)
                .append("updated", updated)
                .append("deleted", deleted)
                .toString();
    }
}
