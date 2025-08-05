package io.github.n4th05.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.n4th05.libraryapi.model.Autor;

import java.util.UUID;
import java.util.List;


//Opcional colocar o @component e o @repository, porque ele já reconhece automaticamente.
// @Component
// @Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);
}
