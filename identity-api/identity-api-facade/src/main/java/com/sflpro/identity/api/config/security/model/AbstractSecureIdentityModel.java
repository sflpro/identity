package com.sflpro.identity.api.config.security.model;

import com.sflpro.identity.api.config.security.accessible.feature.GrantedAccessibleFeature;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/12/20
 *
 * @author Manuk Gharslyan
 */
public abstract class AbstractSecureIdentityModel implements SecureUser {

    private final Set<String> accessibleCustomers;
    private final Set<GrantedAuthority> authorities;
    private final Set<GrantedAccessibleFeature> accessibleFeatures;

    protected AbstractSecureIdentityModel(final Set<String> accessibleCustomers,
                                          final Set<GrantedAuthority> authorities,
                                          final Set<GrantedAccessibleFeature> accessibleFeatures) {
        this.accessibleCustomers = accessibleCustomers;
        this.authorities = authorities;
        this.accessibleFeatures = accessibleFeatures;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractSecureIdentityModel)) return false;

        final AbstractSecureIdentityModel that = (AbstractSecureIdentityModel) o;

        return new EqualsBuilder()
                .append(accessibleCustomers, that.accessibleCustomers)
                .append(authorities, that.authorities)
                .append(accessibleFeatures, that.accessibleFeatures)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accessibleCustomers)
                .append(authorities)
                .append(accessibleFeatures)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accessibleCustomers", accessibleCustomers)
                .append("accessibleFeatures", accessibleFeatures)
                .toString();
    }

    @Override
    public Set<String> accessibleCustomers() {
        return this.accessibleCustomers;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAccessibleFeature> getAccessibleFeatures() {
        return accessibleFeatures;
    }
}