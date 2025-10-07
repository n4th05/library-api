package io.github.n4th05.libraryapi.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
            title = "Library API",
            version = "v1",
            contact = @Contact(
                    name = "Nathalia Machado",
                    email = "n4th.machado@gmail.com",
                    url = "libraryapi.com"
                )
        )
)
public class OpenApiConfiguration {

}
