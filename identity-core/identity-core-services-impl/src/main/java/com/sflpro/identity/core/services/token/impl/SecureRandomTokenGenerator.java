package com.sflpro.identity.core.services.token.impl;


import com.sflpro.identity.core.services.token.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Component
public class SecureRandomTokenGenerator implements TokenGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${tokenService.tokenGenerator.secureRandom.numBits}")
    private int numBits;

    @Value("${tokenService.tokenGenerator.secureRandom.radix}")
    private int radix;

    @Override
    public String generate() {
        return new BigInteger(numBits, secureRandom).toString(radix);
    }
}
