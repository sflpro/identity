package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiException;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class InactiveIdentityExceptionDto extends IdentityApiException {
    public InactiveIdentityExceptionDto(Exception cause) {
        this(null, cause);
    }

    public InactiveIdentityExceptionDto(String message, Exception cause) {
        super(IdentityApiError.INACTIVE_IDENTITY, message, cause);
    }
}
