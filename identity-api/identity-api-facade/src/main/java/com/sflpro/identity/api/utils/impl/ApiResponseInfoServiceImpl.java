package com.sflpro.identity.api.utils.impl;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;
import com.sflpro.identity.api.utils.ApiResponseInfoAspect;
import com.sflpro.identity.api.utils.ApiResponseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public class ApiResponseInfoServiceImpl implements ApiResponseInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ApiResponseInfoAspect.class);

    private final String apiVersion;

    public ApiResponseInfoServiceImpl() {
        this.apiVersion = this.getVersion();
    }

    public void propagateApiResponse(final AbstractApiResponse abstractApiResponse, final Long startTime) {
        abstractApiResponse.setTimeSpent(getTimeElapsed(startTime));
        abstractApiResponse.setApiVersion(apiVersion);
    }

    private long getTimeElapsed(long nanoTimeStart) {
        logger.debug("Calculating elapsed time ");
        long elapsedTime = (System.nanoTime() - nanoTimeStart);
        return elapsedTime / 1000000;
    }

    private String getVersion() {
        String version = null;
        Package aPackage = getClass().getPackage();
        if (aPackage != null) {
            logger.debug("Getting Implementation Version of Identity Api ");
            version = aPackage.getImplementationVersion();
        }
        if (version == null) {
            logger.warn("Unable to determine the version of the application");
        }
        return version;
    }
}
