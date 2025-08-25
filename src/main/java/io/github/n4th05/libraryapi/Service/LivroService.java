package io.github.n4th05.libraryapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.n4th05.libraryapi.model.Livro;
import io.github.n4th05.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

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
}
