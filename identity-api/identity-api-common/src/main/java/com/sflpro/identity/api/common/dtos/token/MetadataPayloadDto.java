package com.sflpro.identity.api.common.dtos.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public final class MetadataPayloadDto<T> {

    private final String key;
    private final Set<T> payloads;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private MetadataPayloadDto(@JsonProperty("key") final String key,
                               @JsonProperty("payloads") final Set<T> payloads) {
        Assert.hasText(key, "The metadata dto key should not be null or empty");
        Assert.notEmpty(payloads, "The metadata dto payloads should not be null");
        this.key = key;
        this.payloads = payloads;
    }

    /**
     * Create a metadata payload
     *
     * @param key
     * @param payloads
     * @param <T>
     * @return
     */
    public static <T> MetadataPayloadDto<T> of(final String key, final Set<T> payloads) {
        return new MetadataPayloadDto<>(key, payloads);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof MetadataPayloadDto)) return false;

        final MetadataPayloadDto<?> that = (MetadataPayloadDto<?>) o;

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

    public String getKey() {
        return key;
    }

    public Set<T> getPayloads() {
        return Collections.unmodifiableSet(payloads);
    }
}