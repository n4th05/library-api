package io.github.n4th05.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.n4th05.libraryapi.model.Autor;

import java.util.UUID;

//Opcional colocar o @component e o @repository, porque ele já reconhece automaticamente.
// @Component
// @Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {

}
