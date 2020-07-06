package com.sflpro.identity.api.client;

import java.io.Closeable;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public interface IdentityApiClient extends Closeable {

    /**
     * @return the {@link AuthResource} instance
     */
    AuthResource auth();

    /**
     * @return the {@link IdentityResource} instance
     */
    IdentityResource identity();

    /**
     * @return the {@link PrincipalResource} instance
     */
    PrincipalResource principal();

    /**
     * @return the {@link ResourceResource} instance
     */
    ResourceResource resource();
}
