package com.sflpro.identity.core.services.notification;

import javax.ws.rs.client.Client;
import java.util.Properties;

/**
 * Company: SFL LLC
 * Created on 09/01/2018
 *
 * @author Davit Harutyunyan
 */
public interface JerseyClientBuilder {

  /**
   * Builds API client
   *
   * @return client
   */
  Client buildClient(final Properties properties);
}
