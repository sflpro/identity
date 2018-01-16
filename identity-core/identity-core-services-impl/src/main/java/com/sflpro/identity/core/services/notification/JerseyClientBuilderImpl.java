package com.sflpro.identity.core.services.notification;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Properties;

/**
 * Company: SFL LLC
 * Created on 09/01/2018
 *
 * @author Davit Harutyunyan
 */
public class JerseyClientBuilderImpl implements JerseyClientBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyClientBuilderImpl.class);

    /* Constructors */
    public JerseyClientBuilderImpl() {
        LOGGER.debug("Initializing jersey client builder");
    }

    @Override
    public Client buildClient(final Properties properties) {
        LOGGER.debug("Building Jersey client");

        final int readTimeOutInMilliseconds =
                Integer.valueOf(properties.getProperty("readTimeOutInMilliseconds"));
        final int connectTimeOutInMilliseconds =
                Integer.valueOf(properties.getProperty("connectTimeOutInMilliseconds"));
        final int connectionMaxCount = Integer.valueOf(properties.getProperty("connectionMaxCount"));
        final int connectionMaxCountPerRoute =
                Integer.valueOf(properties.getProperty("connectionMaxCountPerRoute"));

        final ClientConfig clientConfig = new ClientConfig();
        // values are in milliseconds
        clientConfig.property(ClientProperties.READ_TIMEOUT, readTimeOutInMilliseconds);
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, connectTimeOutInMilliseconds);

        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(connectionMaxCount);
        connectionManager.setDefaultMaxPerRoute(connectionMaxCountPerRoute);

        clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);

        final ApacheConnectorProvider connectorProvider = new ApacheConnectorProvider();
        clientConfig.connectorProvider(connectorProvider);

        final Client client = ClientBuilder.newClient(clientConfig);
        client.register(JacksonFeature.class);

        LOGGER.debug("Successfully created Jersey client - {}", client);
        return client;
    }
}
