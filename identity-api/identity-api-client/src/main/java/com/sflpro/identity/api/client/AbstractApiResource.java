package com.sflpro.identity.api.client;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public class AbstractApiResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected final Client client;
    protected final WebTarget rootTarget;

    protected AbstractApiResource(final Client client, final WebTarget rootTarget, final String pathSuffix) {
        if (client == null || rootTarget == null || StringUtils.isEmpty(pathSuffix)) {
            throw new IllegalStateException("Failed to construct ApiResource instance. Supplied " +
                    "client:" + (client == null) + ", " +
                    "rootTarget:" + (rootTarget == null) + ", " +
                    "pathSuffix:" + StringUtils.isEmpty(pathSuffix));
        }

        String finalPathSuffix = pathSuffix;
        if (!"/".equals(pathSuffix.substring(0, 1))) {
            finalPathSuffix = "/" + pathSuffix;
        }

        this.client = client;
        this.rootTarget = rootTarget.path(finalPathSuffix);
    }

    /**
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @return
     */
    protected Response doGet(final String path) {
        WebTarget target = rootTarget.path(path);

        logger.debug("Doing GET request to {}", target.getUri());

        Response r = target.request().get();
        checkResponseError(r, target);
        return r;
    }

    protected <T> T doGet(final String path, final Class<T> entityType) {
        Response response = null;

        try {
            response = doGet(path);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    protected <T> T doGet(final String path, final GenericType<T> entityType) {
        Response response = null;

        try {
            response = doGet(path);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    /**
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @param queryParams
     * @return
     */
    protected Response doGet(final String path, Map<String, String> queryParams) {
        WebTarget target = rootTarget.path(path);
        if (queryParams != null) {
            for (Map.Entry<String, String> e : queryParams.entrySet())
                target = target.queryParam(e.getKey(), e.getValue());
        }

        logger.debug("Doing GET request to {}", target.getUri());

        Response r = target.request().get();
        checkResponseError(r, target);
        return r;
    }

    protected <T> T doGet(final String path, final Class<T> entityType, Map<String, String> queryParams) {
        Response response = null;

        try {
            response = doGet(path, queryParams);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    protected <T> T doGet(final String path, final GenericType<T> entityType, Map<String, String> queryParams) {
        Response response = null;

        try {
            response = doGet(path, queryParams);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    /**
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @return
     */
    protected Response doPost(final String path, final Object data) {
        WebTarget target = rootTarget.path(path);

        logger.debug("Doing POST request to {}", target.getUri());

        Response response = target.request().post(Entity.entity(data, MediaType.APPLICATION_JSON), Response.class);

        checkResponseError(response, target);
        return response;
    }

    protected <T> T doPost(String path, Object data, Class<T> entityType) {
        Response response = null;

        try {
            response = doPost(path, data);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    protected <T> T doPost(final String path, final Object data, final GenericType<T> entityType) {
        Response response = null;

        try {
            response = doPost(path, data);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    /**
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @return
     */
    protected Response doPut(final String path, final Object data) {
        WebTarget target = rootTarget.path(path);

        logger.debug("Doing PUT request to {}", target.getUri());

        Response response = target.request().put(Entity.entity(data, MediaType.APPLICATION_JSON), Response.class);

        checkResponseError(response, target);
        return response;
    }

    protected <T> T doPut(String path, Object data, Class<T> entityType) {
        Response response = null;

        try {
            response = doPut(path, data);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    protected <T> T doPut(final String path, final Object data, final GenericType<T> entityType) {
        Response response = null;

        try {
            response = doPut(path, data);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    /**
     * Warning: Resources returned by this method should be manually closed!
     *
     * @param path
     * @return
     */
    protected Response doDelete(final String path) {
        WebTarget target = rootTarget.path(path);

        logger.debug("Doing DELETE request to {}", target.getUri());

        Response response = target.request().delete();

        checkResponseError(response, target);
        return response;
    }

    protected <T> T doDelete(String path, Class<T> entityType) {
        Response response = null;

        try {
            response = doDelete(path);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    protected <T> T doDelete(final String path, final GenericType<T> entityType) {
        Response response = null;

        try {
            response = doDelete(path);
            return response.readEntity(entityType);
        } finally {
            closeResponse(response);
        }
    }

    private void checkResponseError(final Response response, final WebTarget target) {
        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            try {
                response.bufferEntity();

                logger.warn("Error(statusCode:{}) during http request to {}", response.getStatus(), target.getUri());

                IdentityApiExceptionDto originalException = response.readEntity(IdentityApiExceptionDto.class);
                Class<? extends IdentityApiExceptionDto> exp = IdentityApiError.getExceptionClass(originalException.getApplicationErrorCode());

                if (exp != null) {
                    throw response.readEntity(exp);
                }

                throw originalException;
            } catch (IdentityApiExceptionDto e) {
                throw e;
            } catch (ProcessingException e) {
                throw new IdentityApiExceptionDto(IdentityApiError.FAILED_TO_PARSE_RESPONSE, e);
            } catch (Exception e) {
                throw new IdentityApiExceptionDto(IdentityApiError.UNKNOWN_CLIENT_ERROR, e);
            }
        }
    }

    private void closeResponse(final Response r) {
        try {
            if (r != null) {
                r.close();
            }
        } catch (Exception e) {
            logger.debug("Error when closing the response object", e);
            // Do nothing here
        }
    }
}
