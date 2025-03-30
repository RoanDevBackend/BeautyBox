package org.beautybox.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${spring.url}")
    private String springUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("bearerAuth");
        return new OpenAPI()
                .info(new Info()
                        .title("Beauty Box")
                        .version("1.0")
                        .description("API")
                        .contact(new Contact()
                                .name(": Roãn Văn Quyền")
                                .email("roan.dev.backend@gmail.com"))
                )
                .addServersItem(new Server()
                        .url(springUrl)
                        .description("Beauty Box"))
                .schemaRequirement("bearerAuth", securityScheme);
    }
}
