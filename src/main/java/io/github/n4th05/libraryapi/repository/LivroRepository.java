package io.github.n4th05.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.n4th05.libraryapi.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, UUID>{
    
}
