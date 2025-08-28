package io.github.n4th05.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.github.n4th05.libraryapi.model.GeneroLivro;
import io.github.n4th05.libraryapi.model.Livro;
import io.github.n4th05.libraryapi.repository.LivroRepository;
import io.github.n4th05.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;

import static io.github.n4th05.libraryapi.repository.Specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro){
        validator.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Livro livro){
        repository.delete(livro);
    }

    public List<Livro> pesquisa(
            String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

        // select * from livro where isbn = :isbn and nomeAutor = 

        // Specification<Livro> specs = Specification
        //         .where(LivroSpecs.isbnEqual(isbn))
        //         .and(LivroSpecs.tituloLike(titulo))
        //         .and(LivroSpecs.generoEqual(genero))
        //         ;


        //select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction() ); // conjunction significa "true"

        if(isbn != null){
            // query = query and isbn = :isbn
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }
        
        return repository.findAll(specs);
    }

    public void atualizar(Livro livro){
        if(livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar é necessario que o livro já esteja salvo na base.");
        }

        validator.validar(livro);
        repository.save(livro);
    }
}
