package com.sflpro.identity.api.common.dtos;

import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Company: SFL LLC
 * Created on 15/02/2018
 *
 * @author Davit Harutyunyan
 */
public interface ApiError extends Serializable {
    String getDefaultMessage();

    Response.Status getResponseHttpStatus();

    long getErrorCode();

    Class<? extends IdentityApiExceptionDto> getExceptionClass();
}
