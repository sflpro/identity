package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenInvalidationRequest {

    private TokenType tokenType;
    private String token;

    public TokenInvalidationRequest(TokenType tokenType, String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TokenInvalidationRequest that = (TokenInvalidationRequest) o;

        return new EqualsBuilder()
                .append(tokenType, that.tokenType)
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tokenType)
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tokenType", tokenType)
                .append("token", token)
                .toString();
    }
}
