package io.github.n4th05.libraryapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.github.n4th05.libraryapi.model.Usuario;
import io.github.n4th05.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    private static final String SENHA_PADRAO = "321";

    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Usuario usuario = usuarioService.obterPorEmail(email);

        if(usuario == null){
            usuario = CadastrarUsuarioNaBase(email);
        }

        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario CadastrarUsuarioNaBase(String email) {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setEmail(email);

        usuario.setLogin(obterLoginApartirEmail(email));

        usuario.setSenha(SENHA_PADRAO); // Senha que o usuário vai usar para logar na aplicação caso ele queira logar sem ser pelo Google.
        usuario.setRoles(List.of("OPERADOR"));
        
        usuarioService.salvar(usuario); // Não precisamos colocar o passwordEncoder.encode porque o salvar já faz isso pra gente.
        return usuario;
    }

    private String obterLoginApartirEmail(String email) {
        return email.substring(0, email.indexOf("@")); // Pega o que está antes do @
    }
}
