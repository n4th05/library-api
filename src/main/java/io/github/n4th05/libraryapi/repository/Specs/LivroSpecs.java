package io.github.n4th05.libraryapi.repository.Specs;

import org.springframework.data.jpa.domain.Specification;

import io.github.n4th05.libraryapi.model.GeneroLivro;
import io.github.n4th05.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

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

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        // and to_char(data_publicacao, 'yyyy') = :anoPublicacao
        return (root, query, cb) -> 
                cb.equal( cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> { // root representa a entidade Livro
        // forma recomendada com join:
            Join<Object,Object> joinAutor = root.join("autor", JoinType.LEFT); // fazer o join com a entidade Autor
            return cb.like (cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");

        // forma simples de fazer sem o join:
            // return cb.like( cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%" );
        };
    }
}
