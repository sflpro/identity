package com.sflpro.identity.api.common.dtos.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public final class MetadataPayloadDto<T> {

    private final String key;
    private final T payload;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private MetadataPayloadDto(@JsonProperty("key") final String key,
                               @JsonProperty("payload") final T payload) {
        Assert.hasText(key, "The metadata dto key should not be null or empty");
        Assert.notNull(payload, "The metadata dto payload should not be null");
        this.key = key;
        this.payload = payload;
    }

    /**
     * Create a metadata payload
     *
     * @param key
     * @param payload
     * @param <T>
     * @return
     */
    public static <T> MetadataPayloadDto<T> of(final String key, final T payload) {
        return new MetadataPayloadDto<>(key, payload);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof MetadataPayloadDto)) return false;

        final MetadataPayloadDto<?> that = (MetadataPayloadDto<?>) o;

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

    public String getKey() {
        return key;
    }

    public T getPayload() {
        return payload;
    }
}