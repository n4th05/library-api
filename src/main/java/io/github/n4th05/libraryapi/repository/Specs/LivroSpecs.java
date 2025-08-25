package io.github.n4th05.libraryapi.repository.Specs;

import org.springframework.data.jpa.domain.Specification;

import io.github.n4th05.libraryapi.model.GeneroLivro;
import io.github.n4th05.libraryapi.model.Livro;

public class LivroSpecs {
    
    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        // upper(livro.titulo) like (%:param%)
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }
}
