package io.github.n4th05.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.github.n4th05.libraryapi.security.CustomUserDetailsService;
import io.github.n4th05.libraryapi.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Habilita a segurança baseada em métodos, permitindo o uso de anotações como @Secured e @RolesAllowed.
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita o CSRF. Porque não estamos usando essa API para aplicações web, então não precisamos dessa proteção.
                .httpBasic(Customizer.withDefaults()) // Habilita a autenticação HTTP Basic.
        //      .formLogin(Customizer.withDefaults()) // Habilita o formulário de login padrão.
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
                .build(); // Constrói o SecurityFilterChain.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ // Codificador de senhas BCrypt com força 10.
        return new BCryptPasswordEncoder(10); // BCrypt é um algoritmo de hash que é usado para proteger senhas, ele criptografa a senha de forma que não possa ser revertida.
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){
        // Comentamos porque ele usa um UserDetailsService em memória, mas queremos usar um UserDetailsService que busca os usuários no banco de dados.

    //     UserDetails user1 = User.builder()
    //     .username("usuario")
    //     .password(encoder.encode("123")) // A senha é codificada usando o PasswordEncoder definido acima.
    //     .roles("USER")
    //     .build();

    //     UserDetails user2 = User.builder()
    //     .username("admin")
    //     .password(encoder.encode("321"))
    //     .roles("ADMIN")
    //     .build();

    //     return new InMemoryUserDetailsManager(user1, user2); // Cria um UserDetailsService em memória com dois usuários.

        return new CustomUserDetailsService(usuarioService); // Usa o CustomUserDetailsService para carregar os detalhes do usuário a partir do banco de dados.
     }
}
