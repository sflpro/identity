package com.sflpro.identity.core.services.auth;

import com.sflpro.identity.core.datatypes.CredentialType;
import com.sflpro.identity.core.db.entities.Credential;
import com.sflpro.identity.core.services.resource.ResourceRequest;
import com.sflpro.identity.core.services.token.TokenRequest;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
public final class AuthenticationRequest<T extends Credential, E extends CredentialIdentifier<T>, S extends AuthenticationRequestDetails<T, E>> {

    @NotNull
    private final S details;

    private final List<TokenRequest> tokenRequests;

    private final List<ResourceRequest> resourceRequests;

    public AuthenticationRequest(@NotNull S details, List<TokenRequest> tokenRequests, List<ResourceRequest> resourceRequests) {
        this.details = details;
        this.tokenRequests = tokenRequests;
        this.resourceRequests = resourceRequests;
    }

    public CredentialType getCredentialType() {
        return details.getCredentialType();
    }

    public S getDetails() {
        return details;
    }

    public List<TokenRequest> getTokenRequests() {
        if (tokenRequests == null)
            return new ArrayList<>();
        return new ArrayList<>(tokenRequests);
    }

    public List<ResourceRequest> getResourceRequests() {
        if (resourceRequests == null)
            return new ArrayList<>();
        return resourceRequests;
    }
}
