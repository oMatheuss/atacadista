package com.example.atacadista.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Atacadista Api",
        description = "Aplicação em java para gerenciar produtos, pedidos e clientes em um sistema atacadista",
        contact = @Contact(name = "GitHub", url = "https://github.com/oMatheuss/atacadista"),
        version = "1.0"
    )
)
public class OpenApiConfig {}