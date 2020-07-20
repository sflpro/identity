package com.sflpro.identity.api.config.security.token.model;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/16/20
 *
 * @author Manuk Gharslyan
 */
public interface TokenModel {

    /**
     * Gets identity id
     *
     * @return
     */
    String identityId();

    /**
     * Gets customer id
     *
     * @return
     */
    String customerId();

    /**
     * Gets customer's id or ids from token
     *
     * @return
     */
    Set<String> accessibleCustomers();

    /**
     * Gets the user permissions
     *
     * @return
     */
    Set<String> permissions();

    /**
     * Gets the customer accessible features
     */
    Set<String> accessibleFeatures();

    /**
     * Gets token expiration time
     *
     * @return
     */
    LocalDateTime expiration();
}