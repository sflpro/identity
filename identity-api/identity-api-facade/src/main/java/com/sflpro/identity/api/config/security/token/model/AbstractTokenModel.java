package com.sflpro.identity.api.config.security.token.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/16/20
 *
 * @author Manuk Gharslyan
 */
public abstract class AbstractTokenModel implements TokenModel {

    private final String identityId;
    private final String customerId;
    private final Set<String> accessibleCustomers;
    private final Set<String> permissions;
    private final Set<String> accessibleFeatures;
    private final LocalDateTime expiration;

    protected AbstractTokenModel(final String identityId,
                                 final String customerId,
                                 final Set<String> accessibleCustomers,
                                 final Set<String> permissions,
                                 final Set<String> accessibleFeatures,
                                 final LocalDateTime expiration) {
        this.accessibleFeatures = accessibleFeatures;
        Assert.hasText(identityId, "The identity id should not be null or empty");
        Assert.notNull(expiration, "The expiration should not be null");
        this.identityId = identityId;
        this.customerId = customerId;
        this.accessibleCustomers = accessibleCustomers;
        this.permissions = permissions;
        this.expiration = expiration;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractTokenModel)) return false;

        final AbstractTokenModel that = (AbstractTokenModel) o;

        return new EqualsBuilder()
                .append(identityId, that.identityId)
                .append(customerId, that.customerId)
                .append(accessibleCustomers, that.accessibleCustomers)
                .append(permissions, that.permissions)
                .append(accessibleFeatures, that.accessibleFeatures)
                .append(expiration, that.expiration)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(identityId)
                .append(customerId)
                .append(accessibleCustomers)
                .append(permissions)
                .append(accessibleFeatures)
                .append(expiration)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("identityId", identityId)
                .append("customerId", customerId)
                .append("customerIds", accessibleCustomers)
                .append("permissions", permissions)
                .append("accessibleFeatures", accessibleFeatures)
                .append("expiration", expiration)
                .toString();
    }

    @Override
    public String identityId() {
        return identityId;
    }

    @Override
    public String customerId() {
        return customerId;
    }

    @Override
    public LocalDateTime expiration() {
        return expiration;
    }

    @Override
    public Set<String> accessibleCustomers() {
        return Collections.unmodifiableSet(accessibleCustomers);
    }

    @Override
    public Set<String> permissions() {
        return Collections.unmodifiableSet(permissions);
    }

    @Override
    public Set<String> accessibleFeatures() {
        return Collections.unmodifiableSet(accessibleFeatures);
    }
}