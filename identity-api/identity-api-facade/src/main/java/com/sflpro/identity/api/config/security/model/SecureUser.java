package com.sflpro.identity.api.config.security.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/12/20
 *
 * @author Manuk Gharslyan
 */
public interface SecureUser extends UserDetails {

    /**
     * Gets identity id
     *
     * @return
     */
    String identityId();

    /**
     * Gets customers ids
     *
     * @return
     */
    Set<String> accessibleCustomers();

    @Override
    default String getPassword() {
        throw new UnsupportedOperationException("The password get operation is not supported");
    }

    @Override
    default String getUsername() {
        throw new UnsupportedOperationException("The username get operation is not supported");
    }

    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    @Override
    default boolean isAccountNonLocked() {
        return true;
    }

    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    default boolean isEnabled() {
        return true;
    }
}