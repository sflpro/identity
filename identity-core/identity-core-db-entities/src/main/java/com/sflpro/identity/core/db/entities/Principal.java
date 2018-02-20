package com.sflpro.identity.core.db.entities;

import com.sflpro.identity.core.datatypes.PrincipalStatus;
import com.sflpro.identity.core.datatypes.PrincipalType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
@Entity
@Table(
        name = "principal"
)
@PrimaryKeyJoinColumn(name = "credential_id")
public class Principal extends Credential {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "principal_type")
    private PrincipalType principalType;

    @Enumerated(EnumType.STRING)
    @Column(name = "principal_status")
    private PrincipalStatus principalStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(PrincipalType principalType) {
        this.principalType = principalType;
    }

    public PrincipalStatus getPrincipalStatus() {
        return principalStatus;
    }

    public void setPrincipalStatus(PrincipalStatus principalStatus) {
        this.principalStatus = principalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Principal principal = (Principal) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, principal.name)
                .append(principalType, principal.principalType)
                .append(principalStatus, principal.principalStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(principalType)
                .append(principalStatus)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("principalType", principalType)
                .append("principalStatus", principalStatus)
                .toString();
    }
}
