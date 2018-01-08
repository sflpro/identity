package com.sflpro.identity.api.common.dtos.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sflpro.identity.api.common.dtos.credential.CredentialDto;

import java.time.LocalDateTime;

/**
 * Company: SFL LLC
 * Created on 23/12/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenDto {

    private String value;

    private String tokenType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expirationDate;

    private CredentialDto issuedBy;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CredentialDto getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(CredentialDto issuedBy) {
        this.issuedBy = issuedBy;
    }
}
