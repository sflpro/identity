package com.sflpro.identity.api.common.dtos.principal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sflpro.identity.api.common.dtos.IdentityApiError;
import com.sflpro.identity.api.common.dtos.IdentityApiExceptionDto;
import com.sflpro.identity.api.common.dtos.NonLoggableException;

/**
 * Company: SFL LLC
 * Created on 17/02/2018
 *
 * @author Davit Harutyunyan
 */
public class PrincipalNameBusyExceptionDto extends IdentityApiExceptionDto implements NonLoggableException {

    public PrincipalNameBusyExceptionDto(@JsonProperty("message") String message) {
        this(message, null);
    }

    public PrincipalNameBusyExceptionDto(Exception cause) {
        this(null, cause);
    }

    public PrincipalNameBusyExceptionDto(String message, Exception cause) {
        super(IdentityApiError.PRINCIPAL_NAME_BUSY, message, cause);
    }
}
