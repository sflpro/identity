package com.sflpro.identity.core.services.identity;

import com.sflpro.identity.core.datatypes.IdentityContactMethod;
import com.sflpro.identity.core.datatypes.IdentityStatus;
import com.sflpro.identity.core.db.entities.Token;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 24/06/2020
 *
 * @author Davit Harutyunyan
 */
public class IdentityResponse {

    private String id;

    private String description;

    private IdentityContactMethod contactMethod;

    private IdentityStatus status;

    private Set<Token> tokens;

    public IdentityResponse() {
        super();
    }

    public IdentityResponse(String id, String description, IdentityContactMethod contactMethod, IdentityStatus status, Set<Token> tokens) {
        this.id = id;
        this.description = description;
        this.contactMethod = contactMethod;
        this.status = status;
        this.tokens = tokens;
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

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IdentityResponse that = (IdentityResponse) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(description, that.description)
                .append(contactMethod, that.contactMethod)
                .append(status, that.status)
                .append(tokens, that.tokens)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(description)
                .append(contactMethod)
                .append(status)
                .append(tokens)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("description", description)
                .append("contactMethod", contactMethod)
                .append("status", status)
                .append("tokens", tokens)
                .toString();
    }
}
