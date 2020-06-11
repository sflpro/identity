package com.sflpro.identity.core.services.token;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public interface TokenGenerator {

    /**
     * Generate jwt token for provided request
     *
     * @param tokenGenerationRequest
     * @return created token
     */
    String generate(TokenGenerationRequest tokenGenerationRequest);
}
