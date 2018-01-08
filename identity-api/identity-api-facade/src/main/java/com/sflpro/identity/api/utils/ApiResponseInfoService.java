package com.sflpro.identity.api.utils;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface ApiResponseInfoService {

    /**
     * Propagates abstract api response's elapsed time and service version
     * @param abstractApiResponse response object
     * @param startTime time elapsed
     */
    void propagateApiResponse(final AbstractApiResponse abstractApiResponse, final Long startTime);
}
