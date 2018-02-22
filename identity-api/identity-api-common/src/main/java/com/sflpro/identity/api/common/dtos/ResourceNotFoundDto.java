package com.sflpro.identity.api.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Company: SFL LLC
 * Created on 11/29/17
 *
 * @author Yervand Aghababyan
 */
public class ResourceNotFoundDto extends IdentityApiExceptionDto {

    public ResourceNotFoundDto(@JsonProperty("message") String message) {
        super(IdentityApiError.NOT_FOUND, message, null);
    }

    public ResourceNotFoundDto(String message, Exception cause) {
        super(IdentityApiError.NOT_FOUND, message, cause);
    }
}
