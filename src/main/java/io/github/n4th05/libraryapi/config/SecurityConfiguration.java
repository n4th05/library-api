package io.github.n4th05.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                    configurer.loginPage("/login"); // Define a página de login personalizada.
                })
                .authorizeHttpRequests(authorize -> {
            //        authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN"); // Exige que o usuário que tenha o papel de ADMIN cadastre um novo autor.
            //        authorize.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN"); // Exige que o usuário que tenha o papel de ADMIN para deletar um autor.
            //        authorize.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN"); // Exige que o usuário que tenha o papel de ADMIN para atualizar um autor.
            //        authorize.requestMatchers(HttpMethod.GET, "/autores/**").hasAnyRole("USER", "ADMIN"); // Exige que o usuário que tenha o papel USER ou ADMIN para acessar endpoints que começam com /autores/.
                    
                    authorize.requestMatchers("/login/**").permitAll(); // Permite acesso à página de login sem autenticação.
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");

                    authorize.anyRequest().authenticated(); // Exige autenticação para todas as requisições. Siginifica que qualquer requisição deve ser autenticada. Tenho que estar autenticado para fazer qualquer requisição.
                })
                .build(); // Constrói o SecurityFilterChain.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ // Codificador de senhas BCrypt com força 10.
        return new BCryptPasswordEncoder(10); // BCrypt é um algoritmo de hash que é usado para proteger senhas, ele criptografa a senha de forma que não possa ser revertida.
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){

        UserDetails user1 = User.builder()
        .username("usuario")
        .password(encoder.encode("123")) // A senha é codificada usando o PasswordEncoder definido acima.
        .roles("USER")
        .build();

        UserDetails user2 = User.builder()
        .username("admin")
        .password(encoder.encode("321"))
        .roles("ADMIN")
        .build();

        return new InMemoryUserDetailsManager(user1, user2); // Cria um UserDetailsService em memória com dois usuários.
    }
}
