package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.services.resource.ResourceRequest;
import com.sflpro.identity.core.services.token.metadata.MetadataPayload;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 7/14/20
 *
 * @author Manuk Gharslyan
 */
public class AccessTokenRequest<T> extends TokenRequest {

    private Set<MetadataPayload<T>> metadataPayloads;

    public AccessTokenRequest(final TokenType tokenType, final ResourceRequest roleResource, final Set<MetadataPayload<T>> metadataPayloads) {
        this(tokenType, null, roleResource, metadataPayloads);
    }

    public AccessTokenRequest(final TokenType tokenType, final Integer expiresInHours, final ResourceRequest roleResource, final Set<MetadataPayload<T>> metadataPayloads) {
        super(tokenType, expiresInHours, roleResource);
        Assert.notNull(metadataPayloads, "The metadataPayloads should not be null");
        this.metadataPayloads = metadataPayloads;
    }

    public Set<MetadataPayload<?>> getMetadataPayloads() {
        return Collections.unmodifiableSet(metadataPayloads);
    }

    public void setMetadataPayloads(final Set<MetadataPayload<T>> metadataPayloads) {
        assertMetadataPayloads(metadataPayloads);
        this.metadataPayloads = metadataPayloads;
    }

    private void assertMetadataPayloads(final Set<MetadataPayload<T>> metadataPayloads) {
        if (metadataPayloads.size() != metadataPayloads.stream()
                .map(MetadataPayload::getKey)
                .collect(Collectors.toUnmodifiableSet())
                .size()) {
            throw new IllegalArgumentException("The metadata should not contain duplicate keys");
        }
    }
}