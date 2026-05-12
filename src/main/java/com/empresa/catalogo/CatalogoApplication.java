package com.empresa.catalogo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// anotaciones para la documentacion de la API
@OpenAPIDefinition(
		info = @Info(
				title = "API Catalogo de Productos",
				version = "1.0",
				description = "API REST para la gestion del catalogo de productos"
		)
)
@SpringBootApplication
public class CatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

}
