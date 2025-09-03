package io.github.n4th05.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import io.github.n4th05.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.model.Usuario;
import io.github.n4th05.libraryapi.repository.AutorRepository;
import io.github.n4th05.libraryapi.repository.LivroRepository;
import io.github.n4th05.libraryapi.security.SecurityService;
import io.github.n4th05.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor já esteja salvo na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um Autor possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    // Método para pesquisa de autores, SIMPLES.
    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        if(nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    // Método para pesquisa usando Query By Example. Recomendado.
    public List<Autor> pesquisarByExample(String nome, String nacionalidade){
        var autor = new Autor();
        
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
        .matching() // Configura o matcher para ignorar campos específicos.
        .withIgnorePaths("id", "dataNascimento", "dataCadastro") // Ignora esses campos na pesquisa.
        .withIgnoreNullValues() // Ignora valores nulos nos campos.
        .withIgnoreCase() // Ignora diferenças entre maiúsculas e minúsculas.
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Faz uma pesquisa que contém o valor do campo.
        Example<Autor> autorExample = Example.of(autor, matcher);

        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

}

