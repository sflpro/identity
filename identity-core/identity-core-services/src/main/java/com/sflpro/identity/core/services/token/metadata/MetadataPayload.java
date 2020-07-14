package com.sflpro.identity.core.services.token.metadata;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 7/14/20
 *
 * @author Manuk Gharslyan
 */
public final class MetadataPayload<T> {

    private String key;
    private Set<T> payloads;
    
    public MetadataPayload(final String key, final Set<T> payloads) {
        Assert.hasText(key, "The metadata key should not be null or empty");
        Assert.notEmpty(payloads, "The metadata payloads should not be null or empty");
        this.key = key;
        this.payloads = payloads;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setPayloads(final Set<T> payloads) {
        this.payloads = payloads;
    }

    public Set<T> getPayloads() {
        return Collections.unmodifiableSet(payloads);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof MetadataPayload)) return false;

        final MetadataPayload<?> that = (MetadataPayload<?>) o;

        return new EqualsBuilder()
                .append(key, that.key)
                .append(payloads, that.payloads)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(key)
                .append(payloads)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("key", key)
                .append("payloads", payloads)
                .toString();
    }
}