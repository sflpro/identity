package com.sflpro.identity.api.common.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 20/02/2018
 *
 * @author Davit Harutyunyan
 */
public class ApiGenericListResponse<T extends AbstractApiResponse> extends AbstractApiResponse {

    private long totalItems;

    private List<T> items = new ArrayList<>();

    public ApiGenericListResponse() {
        super();
    }

    public ApiGenericListResponse(long totalItems, List<T> items) {
        this.totalItems = totalItems;
        this.items = items;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiGenericListResponse<?> that = (ApiGenericListResponse<?>) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(totalItems, that.totalItems)
                .append(items, that.items)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(totalItems)
                .append(items)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("totalItems", totalItems)
                .append("items", items)
                .toString();
    }
}
