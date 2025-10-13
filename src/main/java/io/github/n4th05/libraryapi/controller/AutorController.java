package io.github.n4th05.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.n4th05.libraryapi.controller.dto.AutorDTO;
import io.github.n4th05.libraryapi.controller.mappers.AutorMapper;
import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
// https://localhost:8080/autores
@Tag(name = "Autores")
@Slf4j // Adiciona suporte a logging via Lombok
public class AutorController implements GenericController { // Implementa a interface GenericController

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')") // Apenas usuários com o papel GERENTE podem acessar esse endpoint.
    @Operation(summary = "Salvar", description = "Cadastrar novo Autor.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Autor cadastrado com sucesso."),
        @ApiResponse(responseCode = "422", description = "Erro de validação."),
        @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    }
    )
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        log.info("Cadastrando novo autor: {}", dto.nome());

        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados do autor pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor Encontrado."),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    }
    )
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return service
                .obterPorId(idAutor)
                .map(autor -> { // Aqui usamos o map para transformar o Autor em AutorDTO.
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um Autor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
        @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado.")
        }
    )
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        log.info("Deletando autor de ID: {}", id);

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de Autores por parâmetros.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucesso.")
        }
    )
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = service.pesquisarByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO) // Aqui usamos o mapper para converter cada Autor em AutorDTO.
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um Autor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
        @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
        }
    )
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }

}
