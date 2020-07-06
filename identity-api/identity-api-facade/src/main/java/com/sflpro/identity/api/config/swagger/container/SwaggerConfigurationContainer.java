package com.sflpro.identity.api.config.swagger.container;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Company: SFL LLC
 * Created on 7/1/20
 *
 * @author Manuk Gharslyan
 */
public class SwaggerConfigurationContainer {

    private final String title;
    private final String description;
    private final Contact contact;
    private final SecurityScheme securityScheme;
    private final String securitySchemaName;
    private final Stream<String> packages;

    private SwaggerConfigurationContainer(final SwaggerConfigurationContainerBuilder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.contact = builder.contact;
        this.securityScheme = builder.securityScheme;
        this.securitySchemaName = builder.securitySchemaName;
        this.packages = builder.packages;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Contact getContact() {
        return contact;
    }

    public SecurityScheme getSecurityScheme() {
        return securityScheme;
    }

    public String getSecuritySchemaName() {
        return securitySchemaName;
    }

    public Stream<String> getPackages() {
        return packages;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("title", title)
                .append("description", description)
                .append("contact", contact)
                .append("securityScheme", securityScheme)
                .append("securitySchemaName", securitySchemaName)
                .append("packages", packages)
                .toString();
    }

    /**
     * Gets the swagger configuration container builder
     *
     * @return
     */
    public static SwaggerConfigurationContainerBuilder builder() {
        return new SwaggerConfigurationContainerBuilder();
    }


    public static class SwaggerConfigurationContainerBuilder {
        private String title;
        private String description;
        private Contact contact;
        private SecurityScheme securityScheme;
        private String securitySchemaName;
        private Stream<String> packages;

        private SwaggerConfigurationContainerBuilder() {
        }

        public SwaggerConfigurationContainerBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public SwaggerConfigurationContainerBuilder withDescription(final String description) {
            this.description = description;
            return this;
        }

        public SwaggerConfigurationContainerBuilder withDefaultContact() {
            this.contact = new Contact()
                    .name("Weadapt")
                    .email("info@weadapt.digital")
                    .url("https://weadapt.digital/contact/");
            return this;
        }

        public SwaggerConfigurationContainerBuilder withContact(final Contact contact) {
            if (Objects.nonNull(contact)) {
                this.contact = contact;
                return this;
            }
            return withDefaultContact();
        }

        public SwaggerConfigurationContainerBuilder withDefaultSecurityScheme() {
            this.securityScheme = new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .name("Authorization")
                    .in(SecurityScheme.In.HEADER)
                    .scheme("bearer")
                    .bearerFormat("JWT");
            return this;
        }

        public SwaggerConfigurationContainerBuilder withSecurityScheme(final SecurityScheme securityScheme) {
            if (Objects.nonNull(securityScheme)) {
                this.securityScheme = securityScheme;
                return this;
            }
            return withDefaultSecurityScheme();
        }

        public SwaggerConfigurationContainerBuilder withDefaultSecuritySchemaName() {
            this.securitySchemaName = "JWT";
            return this;
        }

        public SwaggerConfigurationContainerBuilder withSecuritySchemaName(final String securitySchemaName) {
            if (StringUtils.isNoneEmpty(securitySchemaName)) {
                this.securitySchemaName = securitySchemaName;
                return this;
            }
            return withDefaultSecuritySchemaName();
        }

        public SwaggerConfigurationContainerBuilder withPackages(final String... packages) {
            Assert.notNull(packages, "The packages should not be null");
            this.packages = Stream.of(packages);
            return this;
        }

        public SwaggerConfigurationContainer build() {
            return new SwaggerConfigurationContainer(this);
        }
    }
}