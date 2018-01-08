package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
@SwaggerDefinition(tags = {@Tag(name = "status", description = "The status of the identity service with technical details")})
@Api(tags = {"status"})
@Component
@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatusEndpoint {

    @ApiOperation("Returns identity service's status")
    @GET
    @Transactional(readOnly = true)
    public ApiResponseDto getStatus() {
        return new ApiResponseDto();
    }
}
