package io.github.n4th05.libraryapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
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

import io.github.n4th05.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.n4th05.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.n4th05.libraryapi.controller.mappers.LivroMapper;
import io.github.n4th05.libraryapi.model.GeneroLivro;
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
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')") // Apenas usuários com o papel OPERADOR ou GERENTE podem acessar esse endpoint.
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto); // mapear dto para entidade
        service.salvar(livro); // enviar a entidade para o service validar e salvar na base
        var url = gerarHeaderLocation(livro.getId()); // criar url para acesso dos dados do livro
        return ResponseEntity.created(url).build(); // retornar codigo created com header location
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(
        @PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
        .map(livro -> {
            var dto = mapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {

        return service.obterPorId(UUID.fromString(id))
        .map(livro -> {
            service.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value= "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value= "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ){
        Page<Livro> paginaResultado = service.pesquisa(
                isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto){
            return service.obterPorId(UUID.fromString(id))
                    .map(livro -> {
                        Livro entidadeAux = mapper.toEntity(dto);

                        livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                        livro.setIsbn(entidadeAux.getIsbn());
                        livro.setPreco(entidadeAux.getPreco());
                        livro.setGenero(entidadeAux.getGenero());
                        livro.setTitulo(entidadeAux.getTitulo());
                        livro.setAutor(entidadeAux.getAutor());

                        service.atualizar(livro);

                        return ResponseEntity.noContent().build();

            }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
