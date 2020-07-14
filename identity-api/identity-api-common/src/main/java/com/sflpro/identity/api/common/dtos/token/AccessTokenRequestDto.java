package com.sflpro.identity.api.common.dtos.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 7/14/20
 *
 * @author Manuk Gharslyan
 */
public class AccessTokenRequestDto extends TokenRequestDto {

    @JsonProperty("metadata")
    private Set<MetadataPayloadDto<?>> metadataPayloads = Collections.emptySet();
    
    public Set<MetadataPayloadDto<?>> getMetadataPayloads() {
        return Optional.ofNullable(metadataPayloads)
                .map(Collections::unmodifiableSet)
                .orElse(Collections.emptySet());
    }

    public void setMetadataPayloads(final Set<MetadataPayloadDto<?>> metadataPayloads) {
        assertMetadataPayloads(metadataPayloads);
        this.metadataPayloads = Optional.ofNullable(metadataPayloads).orElse(Collections.emptySet());
    }

    private void assertMetadataPayloads(final Set<MetadataPayloadDto<?>> metadataPayloads) {
        Optional.ofNullable(metadataPayloads).ifPresent(theMetaDataPayloads -> {
            if (theMetaDataPayloads.size() != theMetaDataPayloads.stream()
                    .map(MetadataPayloadDto::getKey)
                    .collect(Collectors.toUnmodifiableSet())
                    .size()) {
                throw new IllegalArgumentException("The metadata should not contain duplicate keys");
            }
        });
    }
}