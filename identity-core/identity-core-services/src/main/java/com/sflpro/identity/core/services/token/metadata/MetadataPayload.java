package com.sflpro.identity.core.services.token.metadata;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Company: SFL LLC
 * Created on 7/14/20
 *
 * @author Manuk Gharslyan
 */
public final class MetadataPayload<T> {

    private String key;
    private T payload;

    public MetadataPayload(final String key, final T payload) {
        Assert.hasText(key, "The metadata key should not be null or empty");
        Assert.notNull(payload, "The metadata payload should not be null or empty");
        this.key = key;
        this.payload = payload;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setPayload(final T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof MetadataPayload)) return false;

        final MetadataPayload<?> that = (MetadataPayload<?>) o;

        return new EqualsBuilder()
                .append(key, that.key)
                .append(payload, that.payload)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(key)
                .append(payload)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("key", key)
                .append("payload", payload)
                .toString();
    }
}