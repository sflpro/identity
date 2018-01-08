package com.sflpro.identity.api.common.dtos;

/**
 * Company: SFL LLC
 * Created on 6/14/17
 *
 * @author Sevan Elyasian
 */
public class InvalidRequestException extends IdentityApiException {

    public InvalidRequestException(final String message) {
        super(IdentityApiError.INVALID_REQUEST_DATA, message, null);
    }

    public InvalidRequestException(final String message, final Exception cause) {
        super(IdentityApiError.INVALID_REQUEST_DATA, message, cause);
    }

    public InvalidRequestException(final IdentityApiError error,
                                   final String message,
                                   final int responseStatusCode) {
        super(error, message, responseStatusCode);
    }
}
