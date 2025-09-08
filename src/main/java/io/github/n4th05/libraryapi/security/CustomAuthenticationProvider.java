package io.github.n4th05.libraryapi.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.github.n4th05.libraryapi.model.Usuario;
import io.github.n4th05.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncotrado = usuarioService.obterPorLogin(login);

        if(usuarioEncotrado == null){
            throw getErroUsuarioNaoEncontrado();
        }

        String senhaCriptografada = usuarioEncotrado.getSenha();

        boolean senhasBatem = encoder.matches(senhaDigitada, senhaCriptografada);

        if(senhasBatem){
            return new CustomAuthentication(usuarioEncotrado);
        }
        
        throw getErroUsuarioNaoEncontrado();
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrado(){
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
