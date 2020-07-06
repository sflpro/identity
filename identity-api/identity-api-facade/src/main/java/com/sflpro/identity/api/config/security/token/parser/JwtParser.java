package com.sflpro.identity.api.config.security.token.parser;

import com.sflpro.identity.api.config.security.token.model.TokenModel;

/**
 * Company: SFL LLC
 * Created on 6/30/20
 *
 * @author Manuk Gharslyan
 */
public interface JwtParser {

    /**
     * Parses the jwt token to tokenModel
     *
     * @param token
     * @return
     */
    TokenModel parse(final String token);
}