package org.example.bookshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    public static final String SECURITY_SCHEME = "BearerAuth";
    public static final String SECURITY_SCHEME_BEARER = "bearer";
    public static final String JWT_FORMAT = "JWT";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(SECURITY_SCHEME_BEARER)
                                .bearerFormat(JWT_FORMAT)))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME));
    }
}
