package io.github.n4th05.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.n4th05.libraryapi.model.Autor;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


//Opcional colocar o @component e o @repository, porque ele já reconhece automaticamente.
// @Component
// @Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String Nacionalidade);
}
