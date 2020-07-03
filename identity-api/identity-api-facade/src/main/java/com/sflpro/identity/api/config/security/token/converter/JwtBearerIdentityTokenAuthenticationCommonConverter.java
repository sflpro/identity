package com.sflpro.identity.api.config.security.token.converter;

import com.sflpro.identity.api.config.security.model.IdentityAuthenticationModel;
import com.sflpro.identity.api.config.security.model.SecureIdentityModel;
import com.sflpro.identity.api.config.security.model.SecureUser;
import com.sflpro.identity.api.config.security.token.model.TokenModel;
import com.sflpro.identity.api.config.security.token.parser.JwtParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 6/30/20
 *
 * @author Manuk Gharslyan
 */
public final class JwtBearerIdentityTokenAuthenticationCommonConverter implements Converter<Jwt, IdentityAuthenticationModel> {
    private static final Logger logger = LoggerFactory.getLogger(JwtBearerIdentityTokenAuthenticationCommonConverter.class);

    private final JwtParser jwtParser;

    public JwtBearerIdentityTokenAuthenticationCommonConverter(@Qualifier("identityJwtParser") final JwtParser jwtParser) {
        this.jwtParser = jwtParser;
        logger.debug("Initializing - {}", getClass().getCanonicalName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityAuthenticationModel convert(final Jwt jwt) {
        Assert.notNull(jwt, "The JWT should not be null");
        logger.trace("Processing the conversion of IdentityAuthenticationModel from JWT");
        final TokenModel tokenModel = jwtParser.parse(jwt.getTokenValue());
        final SecureUser secureCommonUserModel = new SecureIdentityModel(
                tokenModel.identityId(),
                tokenModel.accessibleCustomers(),
                tokenModel.permissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableSet())
        );
        final IdentityAuthenticationModel authentication = new IdentityAuthenticationModel(secureCommonUserModel);
        logger.debug("Successfully processed the conversion of IdentityAuthenticationModel from JWT");
        return authentication;
    }
}