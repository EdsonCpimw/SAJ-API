package com.saj.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.Scopes;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("oauth2"))
                .components(new Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .name("oauth2")
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://192.168.0.9:28080/realms/SAJ/protocol/openid-connect/auth")
                                                .tokenUrl("http://192.168.0.9:28080/realms/SAJ/protocol/openid-connect/token")
                                                .scopes(new Scopes().addString("openid", "openid"))
                                        )
                                )
                        )
                )
                .info(new Info()
                        .title("Sistema de Acompanhamento Jurídico")
                        .version("v1")
                        .description("Sistema para gerenciamento e acompanhamento de processos")
                        .termsOfService("https://www.google.com.br/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.google.com.br"))
                );
    }
}
