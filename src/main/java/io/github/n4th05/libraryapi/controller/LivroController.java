package io.github.n4th05.libraryapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.n4th05.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.n4th05.libraryapi.controller.dto.ErroResposta;
import io.github.n4th05.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.n4th05.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {
    
    private final LivroService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            // mapear dto para entidade
            // enviar a entidade para o service validar e salvar na base
            // criar url para acesso dos dados do livro
            // retornar codigo created com header location
            
            return ResponseEntity.ok(dto);
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
