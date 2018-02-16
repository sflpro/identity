package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class InactiveIdentityExceptionDtoDto extends IdentityApiExceptionDto {
    public InactiveIdentityExceptionDtoDto(Exception cause) {
        this(null, cause);
    }

    public InactiveIdentityExceptionDtoDto(String message, Exception cause) {
        super(IdentityApiError.INACTIVE_IDENTITY, message, cause);
    }
}
