package com.sflpro.identity.api.config.security.token.model;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/16/20
 *
 * @author Manuk Gharslyan
 */
public class IdentityTokenModel extends AbstractTokenModel {

    public IdentityTokenModel(final String userId,
                              final String customerId,
                              final Set<String> customerIds,
                              final Set<String> permissions,
                              final Set<String> accessibleFeatures,
                              final LocalDateTime expiration) {
        super(userId, customerId, customerIds, permissions, accessibleFeatures, expiration);
    }
}