package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.util.collection.ImmutableMultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public class AbstractApiResource {
    private static final Logger logger = LoggerFactory.getLogger(AbstractApiResource.class);
    protected static final String URI_DELIMITER = "/";

    protected final Client client;
    protected final WebTarget rootTarget;

    protected AbstractApiResource(final Client client, final WebTarget rootTarget, final String pathPrefix) {
        if (Objects.isNull(client) || Objects.isNull(rootTarget) || StringUtils.isEmpty(pathPrefix)) {
            throw new IllegalStateException("Failed to construct ApiResource instance. Supplied " +
                    "client:" + (client == null) + ", " +
                    "rootTarget:" + (rootTarget == null) + ", " +
                    "pathPrefix:" + StringUtils.isEmpty(pathPrefix));
        }
        String finalPathPrefix = pathPrefix;
        if (!URI_DELIMITER.equals(pathPrefix.substring(0, 1))) {
            finalPathPrefix = URI_DELIMITER + pathPrefix;
        }
        this.client = client;
        this.rootTarget = rootTarget.path(finalPathPrefix);
    }

    // GET http method region begins

    /**
     * Makes Get request by specific path, headers and mediaType
     *
     * @param path
     * @param headers
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doGetWithHeaders(final String path,
                                     final Map<String, String> headers,
                                     final Class<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        Assert.notEmpty(headers, "The headers should not be null or empty");
        try (final Response response = doGetInternally(path, Collections.emptyMap(), headers)) {
            return response.readEntity(responseEntity);
        }
    }

    /**
     * Makes Get request by specific path, headers and mediaType
     *
     * @param path
     * @param headers
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doGetWithHeaders(final String path,
                                     final Map<String, String> headers,
                                     final GenericType<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        Assert.notEmpty(headers, "The headers should not be null or empty");
        try (final Response response = doGetInternally(path, Collections.emptyMap(), headers)) {
            return response.readEntity(responseEntity);
        }
    }

    /**
     * Makes Get request by specific path, headers and mediaType
     *
     * @param path
     * @param queryParams
     * @param headers
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doGetWithQueryParamsAndHeaders(final String path,
                                                   final Map<String, String[]> queryParams,
                                                   final Map<String, String> headers,
                                                   final GenericType<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        Assert.notEmpty(queryParams, "The queryParams should not be null or empty");
        Assert.notEmpty(headers, "The headers should not be null or empty");
        try (final Response response = doGetInternally(path, queryParams, headers)) {
            return response.readEntity(responseEntity);
        }
    }

    // GET http method region ends

    // POST http method region begins

    /**
     * Makes POST request by specific path and data
     *
     * @param path
     * @param data
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doPost(final String path, final Object data, final Class<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        try (final Response response = doPostInternally(path, data, Collections.emptyMap())) {
            return response.readEntity(responseEntity);
        }
    }

    // POST http method region ends

    // PUT http method region begins

    /**
     * Makes PUT request by specific path and data
     *
     * @param path
     * @param data
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doPut(final String path, final Object data, final Class<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        try (final Response response = doPutInternally(path, data, Collections.emptyMap())) {
            return response.readEntity(responseEntity);
        }
    }

    /**
     * Makes PUT request by specific path, headers and mediaType
     *
     * @param path
     * @param headers
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doPutWithHeaders(final String path, final Object data, final Map<String, String> headers, final GenericType<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        Assert.notEmpty(headers, "The headers should not be null or empty");
        try (final Response response = doPutInternally(path, data, headers)) {
            return response.readEntity(responseEntity);
        }
    }

    /**
     * Makes PUT request by specific path, headers and mediaType
     *
     * @param path
     * @param headers
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doPutWithHeaders(final String path, final Object data, final Map<String, String> headers, final Class<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        Assert.notEmpty(headers, "The headers should not be null or empty");
        try (final Response response = doPutInternally(path, data, headers)) {
            return response.readEntity(responseEntity);
        }
    }

    // PUT http method region ends

    // DELETE http method region begins

    /**
     * Makes DELETE request by specific path, headers and mediaType
     *
     * @param path
     * @param headers
     * @param responseEntity
     * @param <R>
     * @return a response entity by given responseEntity type
     */
    protected <R> R doDeleteWithHeaders(final String path, final Map<String, String> headers, final Class<R> responseEntity) {
        Assert.notNull(responseEntity, "The response entity should not be null");
        Assert.notEmpty(headers, "The headers should not be null or empty");
        try (final Response response = doDeleteInternally(path, headers)) {
            return response.readEntity(responseEntity);
        }
    }

    // DELETE http method region begins
    // private methods region begins

    /**
     * Internally does a GET request
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @param headers
     * @param mediaType
     * @return a response entity by given {@link javax.ws.rs.core.Response} type
     */
    private Response doGetInternally(final String path,
                                     final Map<String, String[]> queryParams,
                                     final Map<String, String> headers,
                                     final MediaType... mediaType) {
        Assert.notNull(path, "The path should not be null or empty");
        Assert.notNull(queryParams, "The queryParams should not be null");
        Assert.notNull(headers, "The headers should not be null");
        Assert.notNull(mediaType, "The mediaType should not be null");
        logger.trace("Prepare doGet request to path - {}, with parameters in header...", path);
        WebTarget target = rootTarget.path(path);
        for (Map.Entry<String, String[]> e : queryParams.entrySet())
            target = target.queryParam(e.getKey(), e.getValue());
        logger.debug("Doing GET request to {}", target.getUri());
        final Response response = target.request(mediaType)
                .headers(new ImmutableMultivaluedMap<>(new MultivaluedHashMap<>(headers)))
                .get();
        checkResponseError(response, target);
        return response;
    }

    /**
     * Internally does a POST request
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @param data
     * @param headers
     * @return a response entity by given {@link javax.ws.rs.core.Response} type
     */
    private Response doPostInternally(final String path,
                                      final Object data,
                                      final Map<String, String> headers) {
        Assert.notNull(path, "The path should not be null or empty");
        Assert.notNull(data, "The data should not be null");
        Assert.notNull(headers, "The headers should not be null");
        logger.trace("Prepare doPost request to path - {}, with parameters in header...", path);
        final WebTarget target = rootTarget.path(path);
        logger.debug("Doing POST request to {}", target.getUri());
        final Response response = target.request()
                .headers(new ImmutableMultivaluedMap<>(new MultivaluedHashMap<>(headers)))
                .post(Entity.entity(data, MediaType.APPLICATION_JSON), Response.class);
        checkResponseError(response, target);
        return response;
    }

    /**
     * Internally does a PUT request
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @param data
     * @param headers
     * @return a response entity by given {@link javax.ws.rs.core.Response} type
     */
    private Response doPutInternally(final String path,
                                     final Object data,
                                     final Map<String, String> headers) {
        Assert.notNull(path, "The path should not be null or empty");
        Assert.notNull(data, "The data should not be null");
        Assert.notNull(headers, "The headers should not be null");
        logger.trace("Prepare doPut request to path - {}, with parameters in header...", path);
        final WebTarget target = rootTarget.path(path);
        logger.debug("Doing PUT request to {}", target.getUri());
        final Response response = target.request()
                .headers(new ImmutableMultivaluedMap<>(new MultivaluedHashMap<>(headers)))
                .put(Entity.entity(data, MediaType.APPLICATION_JSON), Response.class);
        checkResponseError(response, target);
        return response;
    }

    /**
     * Internally does DELETE request with specific path and headers
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @return
     */
    private Response doDeleteInternally(final String path, final Map<String, String> headers) {
        Assert.notNull(path, "The path should not be null or empty");
        Assert.notNull(headers, "The request headers should not be null");
        logger.trace("Prepare doPut request to path - {}, with parameters in header...", path);
        final WebTarget target = rootTarget.path(path);
        logger.debug("Doing PUT request to {}", target.getUri());
        final Response response = target.request()
                .headers(new ImmutableMultivaluedMap<>(new MultivaluedHashMap<>(headers)))
                .delete();
        checkResponseError(response, target);
        return response;
    }

    private void checkResponseError(final Response response, final WebTarget target) {
        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            try {
                response.bufferEntity();
                logger.warn("Error(statusCode:{}) during http request to {}", response.getStatus(), target.getUri());
                final IdentityApiExceptionDto originalException = response.readEntity(IdentityApiExceptionDto.class);
                final Class<? extends IdentityApiExceptionDto> exp = IdentityApiError.getExceptionClass(originalException.getApplicationErrorCode());
                if (Objects.nonNull(exp)) {
                    throw response.readEntity(exp);
                }
                throw originalException;
            } catch (final IdentityApiExceptionDto e) {
                throw e;
            } catch (final ProcessingException e) {
                throw new IdentityApiExceptionDto(IdentityApiError.FAILED_TO_PARSE_RESPONSE, e);
            } catch (final Exception e) {
                throw new IdentityApiExceptionDto(IdentityApiError.UNKNOWN_CLIENT_ERROR, format("Unknown client error for response:%s", response), e);
            }
        }
    }
    // private method region ends
}
