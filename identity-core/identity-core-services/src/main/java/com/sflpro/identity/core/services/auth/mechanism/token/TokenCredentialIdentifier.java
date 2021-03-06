package com.sflpro.identity.core.services.auth.mechanism.token;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.datatypes.TokenType;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.auth.CredentialIdentifier;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 01/12/2017
 *
 * @author Davit Harutyunyan
 */
public class TokenCredentialIdentifier extends CredentialIdentifier<Token> {

    @NotNull
    private final TokenType tokenType;

    @NotNull
    private final String token;

    public TokenCredentialIdentifier(@NotNull TokenType tokenType, @NotNull String token) {
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
    public Class<Token> getCredentialClass() {
        return Token.class;
    }
}
