package com.sflpro.identity.api.common.dtos;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
public class SerializationErrorResponseDto extends IdentityApiErrorResponseDto {

    public SerializationErrorResponseDto() {
        super();
    }

    public SerializationErrorResponseDto(final String message) {
        super(IdentityApiError.INVALID_REQUEST_DATA, message);
    }
}
