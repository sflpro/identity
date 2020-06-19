package com.sflpro.identity.core.services.token.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sflpro.identity.core.services.token.TokenGenerationRequest;
import com.sflpro.identity.core.services.token.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Company: SFL LLC
 * Created on 10/06/2020
 *
 * @author Davit Harutyunyan
 */
@Component
@Qualifier("jwtTokenGenerator")
public class JwtTokenGenerator implements TokenGenerator {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenGenerator.class);

    @Value("${jwt.rsa.jwk}")
    private String jwk;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.jws.algorithm}")
    private String jwsAlgorithm;

    /**
     * Generates new RSA Key and returns json String of the key
     */
    private String generateKeyPair() {
        // Generate an EC key pair
        final RSAKey ecJWK;
        try {
            ecJWK = new RSAKeyGenerator(2048, false)
                    .keyID(String.valueOf(UUID.randomUUID()))
                    .keyUse(KeyUse.SIGNATURE)
                    .algorithm(JWSAlgorithm.RS256)
                    .generate();

            return ecJWK.toJSONString();
        } catch (JOSEException e) {
            throw new IllegalStateException("");
        }
    }

    /**
     * Loads RSA key from properties
     *
     * @return
     */
    private RSAKey loadRsaKey() {
        try {
            return (RSAKey) JWK.parse(this.jwk);
        } catch (ParseException e) {
            throw new IllegalStateException(String.format("Failed parsing resource, due to:%s", e), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(final TokenGenerationRequest tokenGenerationRequest) {
        Assert.notNull(tokenGenerationRequest, "tokenGenerationRequest cannot be null");
        Assert.notNull(tokenGenerationRequest.getIdentityId(), "tokenGenerationRequest.identityId cannot be null");
        Assert.notNull(tokenGenerationRequest.getExpiresIn(), "tokenGenerationRequest.expires cannot be null");
        Assert.notNull(tokenGenerationRequest.getRoles(), "tokenGenerationRequest.permissions cannot be null");
        logger.trace("Generating token for generation request:{}...", tokenGenerationRequest);
        final RSAKey rsaJwk = this.loadRsaKey();

        // Create the EC signer
        final RSASSASigner signer;
        try {
            signer = new RSASSASigner(rsaJwk);
        } catch (JOSEException e) {
            throw new IllegalStateException(String.format("Cannot initialize signer for key:%s, due to exception:%s", rsaJwk, e), e);
        }

        // Prepare JWT with claims set
        final Date issuedAt = Date.from((LocalDateTime.now()).toInstant(ZoneOffset.UTC));
        final Date expirationTime = Date.from(LocalDateTime.now().plusSeconds(tokenGenerationRequest.getExpiresIn()).toInstant(ZoneOffset.UTC));
        final JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .claim("identity_id", tokenGenerationRequest.getIdentityId())
                .claim("roles", tokenGenerationRequest.getRoles())
                .issuer(jwtIssuer)
                .issueTime(issuedAt)
                .expirationTime(expirationTime);
        if (tokenGenerationRequest.getResources() != null) {
            for (Map.Entry<String, List<String>> resource : tokenGenerationRequest.getResources().entrySet()) {
                builder.claim(resource.getKey(), resource.getValue());
            }
        }
        final JWTClaimsSet claimsSet = builder
                .build();

        final SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.parse(jwsAlgorithm)).keyID(rsaJwk.getKeyID()).build(),
                claimsSet);

        try {
            // Compute the EC signature
            signedJWT.sign(signer);
            // Serialize the JWS to compact form
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new IllegalStateException(String.format("Cannot sign jwt token, due to:%s", e), e);
        }
    }

    /**
     * Gets publish jwk as json object
     *
     * @return
     */
    public Object jwks() {
        logger.trace("Getting public jwk key...");
        final RSAKey rsaKey = this.loadRsaKey();
        final RSAKey rsaPublicJWK = rsaKey.toPublicJWK();
        final Object jsonObject = rsaPublicJWK.toJSONObject();
        logger.debug("Done getting public jwk key...");
        return jsonObject;
    }
}
