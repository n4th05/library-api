package io.github.n4th05.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Annotation de Auditoria 
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
		
}
