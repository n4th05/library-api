package io.github.n4th05.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.n4th05.libraryapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
    Usuario findByLogin(String login);
}
