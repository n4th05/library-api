package io.github.n4th05.libraryapi.service;

import org.springframework.stereotype.Service;

import io.github.n4th05.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
}
