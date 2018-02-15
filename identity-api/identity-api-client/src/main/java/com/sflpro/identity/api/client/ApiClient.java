package com.sflpro.identity.api.client;

import java.io.Closeable;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public interface ApiClient extends Closeable {

    IdentityResource identity();
}
