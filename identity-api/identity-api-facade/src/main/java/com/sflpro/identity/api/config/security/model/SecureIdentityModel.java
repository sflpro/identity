package com.sflpro.identity.api.config.security.model;

import com.sflpro.identity.api.config.security.accessible.feature.GrantedAccessibleFeature;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/30/20
 *
 * @author Manuk Gharslyan
 */
public class SecureIdentityModel extends AbstractSecureIdentityModel {
    private final String identity;

    public SecureIdentityModel(final String identity,
                               final Set<String> accessibleCustomers,
                               final Set<GrantedAuthority> authorities,
                               final Set<GrantedAccessibleFeature> accessibleFeatures) {
        super(accessibleCustomers, authorities, accessibleFeatures);
        this.identity = identity;
    }

    @Override
    public String identityId() {
        return identity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SecureIdentityModel)) return false;

        final SecureIdentityModel that = (SecureIdentityModel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(identity, that.identity)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(identity)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("identity", identity)
                .toString();
    }
}