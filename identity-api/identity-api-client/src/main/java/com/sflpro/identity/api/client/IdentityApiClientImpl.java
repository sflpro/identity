package com.sflpro.identity.api.client;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sflpro.identity.api.common.dtos.ApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiError;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.filter.EncodingFilter;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.message.GZipEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public class IdentityApiClientImpl implements IdentityApiClient {

    private final Client client;

    private final AuthResource authResource;
    private final IdentityResource identityResource;
    private final PrincipalResource principalResource;
    private final ResourceResource resourceResource;

    /**
     * @param apiUrl The fully qualified URL of the internal API (e.g. http://10.89.1.7:89/rest )
     */
    public IdentityApiClientImpl(final String apiUrl) {
        final SimpleModule module = new SimpleModule("apiErrorMapper", Version.unknownVersion());
        module.addAbstractTypeMapping(ApiError.class, IdentityApiError.class);
        final ObjectMapper mapper = new ObjectMapper().registerModules(new JavaTimeModule(), module)
                .configure(MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES, false);
        final ClientConfig cc = new ClientConfig().property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true)
                .register(GZipEncoder.class)
                .register(EncodingFilter.class)
                .register(new JacksonJsonProvider(mapper));
        client = ClientBuilder.newClient(cc);
        final WebTarget rootTarget = client.target(apiUrl);
        authResource = new AuthResource(client, rootTarget);
        identityResource = new IdentityResource(client, rootTarget);
        principalResource = new PrincipalResource(client, rootTarget);
        resourceResource = new ResourceResource(client, rootTarget);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResource auth() {
        return authResource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityResource identity() {
        return identityResource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrincipalResource principal() {
        return principalResource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceResource resource() {
        return resourceResource;
    }

    @Override
    public void close() {
        client.close();
    }
}
