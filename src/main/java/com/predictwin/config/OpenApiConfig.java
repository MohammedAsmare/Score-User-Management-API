package com.predictwin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                final String securitySchemeName = "bearerAuth";

                return new OpenAPI()
                                .components(new Components().addSecuritySchemes(securitySchemeName,
                                                new SecurityScheme()
                                                                .name(securitySchemeName)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")))
                                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                                .info(new Info()
                                                .title("Score User Management API")
                                                .version("v0.0.1")
                                                .description("API for managing users, matches and predictions")
                                                .contact(new Contact().name("PredictWin Team")
                                                                .email("support@predictwin.example"))
                                                .license(new License().name("MIT")
                                                                .url("https://opensource.org/licenses/MIT")));
        }
}
