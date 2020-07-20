package com.sflpro.identity.api.config.security.accessible.feature.impl;

import com.sflpro.identity.api.config.security.accessible.feature.GrantedAccessibleFeature;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Company: SFL LLC
 * Created on 7/20/20
 *
 * @author Manuk Gharslyan
 */
public final class SimpleIdentityGrantedAccessibleFeature implements GrantedAccessibleFeature {
    private final String accessibleFeature;

    public SimpleIdentityGrantedAccessibleFeature(final String accessibleFeature) {
        Assert.hasText(accessibleFeature, "The granted accessible feature should not be null or empty");
        this.accessibleFeature = accessibleFeature;
    }

    @Override
    public String getAccessibleFeature() {
        return accessibleFeature;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SimpleIdentityGrantedAccessibleFeature)) return false;

        final SimpleIdentityGrantedAccessibleFeature that = (SimpleIdentityGrantedAccessibleFeature) o;

        return new EqualsBuilder()
                .append(accessibleFeature, that.accessibleFeature)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accessibleFeature)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("accessibleFeature", accessibleFeature)
                .toString();
    }
}