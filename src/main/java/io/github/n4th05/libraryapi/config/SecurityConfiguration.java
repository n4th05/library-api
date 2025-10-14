package io.github.n4th05.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import io.github.n4th05.libraryapi.security.JwtCustomAuthenticationFilter;
import io.github.n4th05.libraryapi.security.LoginSocialSucessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Habilita a segurança baseada em métodos, permitindo o uso de anotações como @Secured e @RolesAllowed.
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSucessHandler sucessHandler, JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita o CSRF. Porque não estamos usando essa API para aplicações web, então não precisamos dessa proteção.
                .formLogin(configurer -> {
                    configurer.loginPage("/login"); // Define a página de login personalizada.
                })
                .authorizeHttpRequests(authorize -> {
            //        authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN"); // Exige que o usuário que tenha o papel de ADMIN cadastre um novo autor.
            //        authorize.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN"); // Exige que o usuário que tenha o papel de ADMIN para deletar um autor.
            //        authorize.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN"); // Exige que o usuário que tenha o papel de ADMIN para atualizar um autor.
            //        authorize.requestMatchers(HttpMethod.GET, "/autores/**").hasAnyRole("USER", "ADMIN"); // Exige que o usuário que tenha o papel USER ou ADMIN para acessar endpoints que começam com /autores/.
                    
                    authorize.requestMatchers("/login/**").permitAll(); // Permite acesso à página de login sem autenticação.
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

                    authorize.anyRequest().authenticated(); // Exige autenticação para todas as requisições. Siginifica que qualquer requisição deve ser autenticada. Tenho que estar autenticado para fazer qualquer requisição.
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                        .loginPage("/login") // Define a página de login personalizada para OAuth2. A mesma página que usamos acima.
                        .successHandler(sucessHandler); // Ele vai chamar o nosso handler quando o login for bem sucedido.
                })
                .oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults())) // Habilita a configuração padrão JWT. Habilita o Resource Server para validar tokens JWT.
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class) // Adiciona o filtro customizado após o filtro padrão de autenticação por token Bearer.
                .build(); // Constrói o SecurityFilterChain.
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers( // Ignora a segurança para os endpoints do Swagger UI e do OpenAPI.
                "/v2/api-docs/**",
                "/v3/api-docs",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/actuator/**" // Ignora a segurança para os endpoints do Actuator.
            );
        };

    // Configura o prefixo das roles.
     @Bean
     public GrantedAuthorityDefaults grantedAuthorityDefaults(){ // Remove o prefixo "ROLE_" padrão do Spring Security.
        return new GrantedAuthorityDefaults(""); // Define o prefixo como uma string vazia.
     }

     // Configura, no token JWT, o prefixo SCOPE.
     @Bean
     public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix(""); // Remove o prefixo "SCOPE_" padrão do Spring Security.

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
     }
}
