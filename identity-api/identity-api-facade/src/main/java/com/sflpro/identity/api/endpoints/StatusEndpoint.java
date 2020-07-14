package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Company: SFL LLC
 * Created on 11/29/17
 *
 * @author Yervand Aghababyan
 */
@Component
@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatusEndpoint {

    @Operation(tags = {"status"}, summary = "Returns identity service's status")
    @GET
    @Transactional(readOnly = true)
    @PermitAll
    public ApiResponseDto getStatus() {
        return new ApiResponseDto();
    }
}
