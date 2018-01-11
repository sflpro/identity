package com.sflpro.identity.core.services.token;

import com.sflpro.identity.core.datatypes.TokenType;

/**
 * Company: SFL LLC
 * Created on 03/12/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenDemarcationAsUsedRequest {

    private TokenType tokenType;
    private String tokenValue;
    private String tokenOwnerID;

    public TokenDemarcationAsUsedRequest(TokenType tokenType, String tokenValue, String tokenOwnerID) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
        this.tokenOwnerID = tokenOwnerID;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public String getTokenOwnerID() {
        return tokenOwnerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenDemarcationAsUsedRequest)) return false;

        TokenDemarcationAsUsedRequest that = (TokenDemarcationAsUsedRequest) o;

        if (tokenType != that.tokenType) return false;
        if (!tokenValue.equals(that.tokenValue)) return false;
        return tokenOwnerID != null ? tokenOwnerID.equals(that.tokenOwnerID) : that.tokenOwnerID == null;

    }

    @Override
    public int hashCode() {
        int result = tokenType.hashCode();
        result = 31 * result + tokenValue.hashCode();
        result = 31 * result + (tokenOwnerID != null ? tokenOwnerID.hashCode() : 0);
        return result;
    }
}
