package com.sflpro.identity.core.db.entities;

import com.sflpro.identity.core.datatypes.IdentityContactMethod;
import com.sflpro.identity.core.datatypes.IdentityStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
@Entity
@Table(
        name = "identity"
)
public class Identity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "secret")
    private String secret;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_method")
    private IdentityContactMethod contactMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private IdentityStatus status;
    
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Identity creatorId;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @Column(name = "deleted")
    private LocalDateTime deleted;

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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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

    public Identity getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Identity creatorId) {
        this.creatorId = creatorId;
    }

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        updated = created;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Identity identity = (Identity) o;

        return new EqualsBuilder()
                .append(id, identity.id)
                .append(creatorId, identity.creatorId)
                .append(description, identity.description)
                .append(secret, identity.secret)
                .append(contactMethod, identity.contactMethod)
                .append(status, identity.status)
                .append(created, identity.created)
                .append(updated, identity.updated)
                .append(deleted, identity.deleted)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(creatorId)
                .append(description)
                .append(secret)
                .append(contactMethod)
                .append(status)
                .append(created)
                .append(updated)
                .append(deleted)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("creatorId", creatorId)
                .append("description", description)
                .append("secret", secret)
                .append("contactMethod", contactMethod)
                .append("status", status)
                .append("created", created)
                .append("updated", updated)
                .append("deleted", deleted)
                .toString();
    }
}
