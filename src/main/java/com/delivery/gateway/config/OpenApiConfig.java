package com.delivery.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Documentação agregada do delivery-marketplace.
 * O Swagger UI do gateway lista os /v3/api-docs de cada microsserviço.
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI gatewayOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Delivery Marketplace API")
                        .version("v1")
                        .description("""
                                Ponto de entrada único do marketplace.

                                Use o seletor **Select a definition** no topo da página para navegar
                                entre identity, tenant, catalog e order.
                                """))
                .servers(List.of(new Server()
                        .url("http://localhost:" + serverPort)
                        .description("API Gateway")))
                .components(new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token retornado por POST /api/auth/login")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}
