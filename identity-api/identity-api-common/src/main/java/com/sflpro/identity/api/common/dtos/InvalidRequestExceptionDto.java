package com.sflpro.identity.api.common.dtos;

/**
 * Company: SFL LLC
 * Created on 6/14/17
 *
 * @author Sevan Elyasian
 */
public class InvalidRequestExceptionDto extends IdentityApiExceptionDto {

    public InvalidRequestExceptionDto(final String message) {
        super(IdentityApiError.INVALID_REQUEST_DATA, message, null);
    }

    public InvalidRequestExceptionDto(final String message, final Exception cause) {
        super(IdentityApiError.INVALID_REQUEST_DATA, message, cause);
    }

    public InvalidRequestExceptionDto(final IdentityApiError error,
                                      final String message,
                                      final int responseStatusCode) {
        super(error, message, responseStatusCode);
    }
}
