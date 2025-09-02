package io.github.n4th05.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita o CSRF. Porque não estamos usando essa API para aplicações web, então não precisamos dessa proteção.
                .httpBasic(Customizer.withDefaults()) // Habilita a autenticação HTTP Basic.
        //      .formLogin(Customizer.withDefaults()) // Habilita o formulário de login padrão.
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll(); // Define a página de login personalizada.
                })
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated(); // Exige autenticação para todas as requisições. Siginifica que qualquer requisição deve ser autenticada. Tenho que estar autenticado para fazer qualquer requisição.
                })
                .build(); // Constrói o SecurityFilterChain.
    }
}
