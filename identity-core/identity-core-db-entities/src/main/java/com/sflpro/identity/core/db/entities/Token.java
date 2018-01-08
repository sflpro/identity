package com.sflpro.identity.core.db.entities;

import com.sflpro.identity.core.datatypes.CredentialType;
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

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_by")
    private Credential issuedBy;

    public Token() {
        super();
    }

    public Token(Credential credential, String value, String tokenType, LocalDateTime expirationDate, Credential issuedBy) {
        super(credential);
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

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
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

        Token token1 = (Token) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(value, token1.value)
                .append(expirationDate, token1.expirationDate)
                .append(issuedBy, token1.issuedBy)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(value)
                .append(expirationDate)
                .append(issuedBy)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("value", value)
                .append("expiration_date", expirationDate)
                .append("issuedBy", issuedBy)
                .toString();
    }
}
