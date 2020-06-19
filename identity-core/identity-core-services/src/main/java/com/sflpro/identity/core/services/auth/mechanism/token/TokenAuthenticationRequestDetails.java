package com.sflpro.identity.core.services.auth.mechanism.token;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.AuthenticationRequestDetails;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 01/12/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenAuthenticationRequestDetails extends AuthenticationRequestDetails<Token, TokenCredentialIdentifier> {

    @NotNull
    private final TokenType tokenType;

    @NotNull
    private final String token;

    public TokenAuthenticationRequestDetails(@NotNull TokenType tokenType, @NotNull String token) {
        super(CredentialType.TOKEN);
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
    public TokenCredentialIdentifier getCredentialIdentifier() {
        return new TokenCredentialIdentifier(tokenType, token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TokenAuthenticationRequestDetails that = (TokenAuthenticationRequestDetails) o;

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
                .appendSuper(super.toString())
                .append("tokenType", tokenType)
                .append("token", token)
                .toString();
    }
}
