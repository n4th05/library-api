package io.github.n4th05.libraryapi.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.n4th05.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.n4th05.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.n4th05.libraryapi.controller.mappers.LivroMapper;
import io.github.n4th05.libraryapi.model.Livro;
import io.github.n4th05.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto); // mapear dto para entidade
        service.salvar(livro); // enviar a entidade para o service validar e salvar na base
        var url = gerarHeaderLocation(livro.getId()); // criar url para acesso dos dados do livro
        return ResponseEntity.created(url).build(); // retornar codigo created com header location
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(
        @PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
        .map(livro -> {
            var dto = mapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
