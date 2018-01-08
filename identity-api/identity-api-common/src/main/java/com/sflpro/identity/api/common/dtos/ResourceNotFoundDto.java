package com.sflpro.identity.api.common.dtos;

/**
 * Company: SFL LLC
 * Created on 11/29/17
 *
 * @author Yervand Aghababyan
 */
public class ResourceNotFoundDto extends IdentityApiException {
    public ResourceNotFoundDto(String message, Exception cause) {
        super(IdentityApiError.NOT_FOUND, message, cause);
    }
}
