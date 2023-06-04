package com.example.devinhousemodulo_3_projeto_avaliativo_1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger OpenAPI", version = "1", description = "API Desenvolvida para o Desafio 1 - Modulo 3 da DevInHouse Philips"))
public class DeVinHouseModulo3ProjetoAvaliativo1Application {

    public static void main(String[] args) {
        SpringApplication.run(DeVinHouseModulo3ProjetoAvaliativo1Application.class, args);
    }

}
