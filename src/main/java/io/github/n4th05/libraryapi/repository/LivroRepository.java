package io.github.n4th05.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, UUID>{

    //Query Method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);
    
}
