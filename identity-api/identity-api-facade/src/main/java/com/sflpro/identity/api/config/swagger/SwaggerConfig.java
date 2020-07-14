package com.sflpro.identity.api.config.swagger;

import com.sflpro.identity.api.config.swagger.container.SwaggerConfigurationContainer;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 23/11/2017
 *
 * @author Davit Harutyunyan
 */
@Configuration
public class SwaggerConfig {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${weadapt.swagger.title}")
    private String title;

    @Value("${weadapt.swagger.description}")
    private String description;

    @Value("${weadapt.swagger.packages}")
    private String[] packages;

    @Bean
    public OpenAPI openAPI() {
        return openApi(SwaggerConfigurationContainer.builder()
                .withTitle(title)
                .withDescription(description)
                .withPackages(packages)
                .withDefaultContact()
                .withDefaultSecuritySchemaName()
                .withDefaultSecurityScheme()
                .build());
    }

    private OpenAPI openApi(final SwaggerConfigurationContainer container) {
        logger.trace("Configuring openApi...");
        final OpenAPI oas = new OpenAPI();
        final Info info = new Info()
                .title(container.getTitle())
                .description(container.getDescription())
                .version(getClass().getPackage().getImplementationVersion())
                .contact(container.getContact());
        oas.info(info)
                .components(new Components().addSecuritySchemes(container.getSecuritySchemaName(), container.getSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(container.getSecuritySchemaName()));
        final SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(false)
                .resourcePackages(container.getPackages().collect(Collectors.toSet()));
        try {
            final OpenAPI openAPI = new JaxrsOpenApiContextBuilder<>()
                    .openApiConfiguration(oasConfig)
                    .buildContext(true)
                    .read();
            logger.trace("Done configuring openApi!");
            return openAPI;
        } catch (final OpenApiConfigurationException e) {
            throw new RuntimeException(String.format("Failed to configure swagger, due to:%s", e.getMessage()), e);
        }
    }
}
