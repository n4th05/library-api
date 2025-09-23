package io.github.n4th05.libraryapi.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {
    
    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .build();
    }

    @Bean
    public ClientSettings clientsSettings() {
        return ClientSettings.builder()
                .requireAuthorizationConsent(false) // Se true, aparecerá uma tela de consentimento para o usuário, perguntando se ele concorda em fornecer as informações pessoais dele solicitadas pelo cliente.
                .build();
    }
}
