package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.token.TokenDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 24/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityWithTokenDto extends IdentityDto {

    private Set<TokenDto> tokens;

    public Set<TokenDto> getTokens() {
        return tokens;
    }

    public void setTokens(Set<TokenDto> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IdentityWithTokenDto that = (IdentityWithTokenDto) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(tokens, that.tokens)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(tokens)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("tokens", tokens)
                .toString();
    }
}
