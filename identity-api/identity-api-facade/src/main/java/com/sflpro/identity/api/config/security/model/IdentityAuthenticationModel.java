package com.sflpro.identity.api.config.security.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Company: SFL LLC
 * Created on 6/16/20
 *
 * @author Manuk Gharslyan
 */
public class IdentityAuthenticationModel extends AbstractAuthenticationToken {

    private final UserDetails user;
    private boolean authenticated = true;

    public IdentityAuthenticationModel(final UserDetails user) {
        super(user.getAuthorities());
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return super.getDetails();
    }

    @Override
    public UserDetails getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof IdentityAuthenticationModel)) return false;

        final IdentityAuthenticationModel that = (IdentityAuthenticationModel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(authenticated, that.authenticated)
                .append(user, that.user)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(user)
                .append(authenticated)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}