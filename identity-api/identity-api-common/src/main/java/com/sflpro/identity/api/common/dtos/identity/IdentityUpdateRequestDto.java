package com.sflpro.identity.api.common.dtos.identity;

import com.sflpro.identity.api.common.dtos.principal.PrincipalUpdateRequestDto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 27/11/2017
 *
 * @author Davit Harutyunyan
 */
public class IdentityUpdateRequestDto {

    @NotEmpty
    private String description;

    private String secret;

    private String newSecret;

    private List<PrincipalUpdateRequestDto> principals;

    public IdentityUpdateRequestDto() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getNewSecret() {
        return newSecret;
    }

    public void setNewSecret(String newSecret) {
        this.newSecret = newSecret;
    }

    public List<PrincipalUpdateRequestDto> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<PrincipalUpdateRequestDto> principals) {
        this.principals = principals;
    }
}
