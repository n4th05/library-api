package io.github.n4th05.libraryapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.github.n4th05.libraryapi.model.GeneroLivro;
import io.github.n4th05.libraryapi.model.Livro;
import io.github.n4th05.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

import static io.github.n4th05.libraryapi.repository.Specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro){
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Livro livro){
        repository.delete(livro);
    }

    public List<Livro> pesquisa(
            String isbn, String titulo, String nomeAutor, GeneroLivro genero, LocalDate dataPublicacao){

        // select * from livro where isbn = :isbn and nomeAutor = 

        // Specification<Livro> specs = Specification
        //         .where(LivroSpecs.isbnEqual(isbn))
        //         .and(LivroSpecs.tituloLike(titulo))
        //         .and(LivroSpecs.generoEqual(genero))
        //         ;


        //select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction() );

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
        
        return repository.findAll(specs);
    }
}
