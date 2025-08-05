package io.github.n4th05.libraryapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.repository.AutorRepository;

@Service
public class AutorService {

    private final AutorRepository repository;
    
    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }
}
