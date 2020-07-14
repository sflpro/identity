package com.sflpro.identity.api.config.security.token.parser.impl;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.sflpro.identity.api.config.security.token.model.IdentityTokenModel;
import com.sflpro.identity.api.config.security.token.model.TokenModel;
import com.sflpro.identity.api.config.security.token.parser.JwtParser;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 6/30/20
 *
 * @author Manuk Gharslyan
 */
@Component("identityJwtParser")
public class IdentityJwtParserImpl implements JwtParser {
    private static final Logger logger = LoggerFactory.getLogger(IdentityJwtParserImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenModel parse(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        logger.trace("Processing the parsing of TokenModel from JWT");
        try {
            final JWTClaimsSet claims = JWTParser.parse(token).getJWTClaimsSet();
            final JSONObject resource = claims.getJSONObjectClaim("resource");
            logger.debug("Successfully processed the parsing of WeadaptTokenModel from JWT");
            return new IdentityTokenModel(
                    claims.getStringClaim("identity_id"),
                    mapCustomerId(resource),
                    Set.of(Optional.ofNullable(claims.getStringArrayClaim("customer"))
                            .orElse(ArrayUtils.EMPTY_STRING_ARRAY)),
                    Set.of(Optional.ofNullable(claims.getStringArrayClaim("permissions"))
                            .orElse(ArrayUtils.EMPTY_STRING_ARRAY)),
                    mapExpirationLocalToDateTime(claims.getExpirationTime())
            );
        } catch (final ParseException e) {
            logger.error("The parsing of token model from jwt token done with error : {}", e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    private String mapCustomerId(final JSONObject resource) {
        return Optional.ofNullable(resource)
                .map(theResource -> theResource.getAsString("identifier"))
                .orElse(null);
    }

    private LocalDateTime mapExpirationLocalToDateTime(final Date expirationTime) {
        return expirationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}