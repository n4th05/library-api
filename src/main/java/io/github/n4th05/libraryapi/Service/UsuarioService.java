package io.github.n4th05.libraryapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.n4th05.libraryapi.model.Usuario;
import io.github.n4th05.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha)); // Codifica a senha antes de salvar no banco de dados.
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return repository.findByLogin(login);
    }
}
