package com.sflpro.identity.api.config.security.accessible.feature;

import java.io.Serializable;

/**
 * Company: SFL LLC
 * Created on 7/20/20
 *
 * @author Manuk Gharslyan
 */
public interface GrantedAccessibleFeature extends Serializable {

    /**
     * @return the granted accessible feature
     */
    String getAccessibleFeature();
}