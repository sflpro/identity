package com.sflpro.identity.core.db.entities;

import com.sflpro.identity.core.datatypes.TokenType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
@Entity
@Table(
        name = "token",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_token_type", columnNames = {"value", "token_type"})
        }
)
@PrimaryKeyJoinColumn(name = "credential_id")
public class Token extends Credential {

    @Column(name = "value")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_by")
    private Credential issuedBy;

    public Token() {
        super();
    }

    public Token(/*Credential credential, */String value, TokenType tokenType,
                 LocalDateTime expirationDate, Credential issuedBy) {
        super();
        /*super(credential);*/
        this.value = value;
        this.tokenType = tokenType;
        this.expirationDate = expirationDate;
        this.issuedBy = issuedBy;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Credential getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Credential issuedBy) {
        this.issuedBy = issuedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(value, token.value)
                .append(tokenType, token.tokenType)
                .append(expirationDate, token.expirationDate)
                .append(issuedBy, token.issuedBy)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(value)
                .append(tokenType)
                .append(expirationDate)
                .append(issuedBy)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("tokenType", tokenType)
                .append("expirationDate", expirationDate)
                .append("issuedBy", issuedBy)
                .toString();
    }
}
