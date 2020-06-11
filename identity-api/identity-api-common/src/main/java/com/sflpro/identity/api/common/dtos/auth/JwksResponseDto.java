package com.sflpro.identity.api.common.dtos.auth;

import java.util.Set;

/**
 * Company: SFL LLC
 * Created on 11/06/2020
 *
 * @author Davit Harutyunyan
 */
public class JwksResponseDto {

    private Set<Object> keys;

    public JwksResponseDto() {
        super();
    }

    public JwksResponseDto(Set<Object> keys) {
        this.keys = keys;
    }

    public Set<Object> getKeys() {
        return keys;
    }

    public void setKeys(Set<Object> keys) {
        this.keys = keys;
    }
}
