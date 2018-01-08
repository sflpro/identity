package com.sflpro.identity.api.common.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Company: SFL LLC
 * Created on 28/11/2017
 *
 * @author Davit Harutyunyan
 */
public abstract class AbstractApiResponse {

    private long timeSpent;

    private String apiVersion;

    public AbstractApiResponse() {
        super();
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AbstractApiResponse that = (AbstractApiResponse) o;

        return new EqualsBuilder()
                .append(timeSpent, that.timeSpent)
                .append(apiVersion, that.apiVersion)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(timeSpent)
                .append(apiVersion)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timeSpent", timeSpent)
                .append("apiVersion", apiVersion)
                .toString();
    }
}
