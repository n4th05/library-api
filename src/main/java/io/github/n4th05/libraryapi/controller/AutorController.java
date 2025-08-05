package io.github.n4th05.libraryapi.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.n4th05.libraryapi.Service.AutorService;
import io.github.n4th05.libraryapi.controller.dto.AutorDTO;
import io.github.n4th05.libraryapi.model.Autor;

@RestController
@RequestMapping("/autores")
// https://localhost:8080/autores
public class AutorController {

    public final AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);


        // https://localhost:8080/autores/0727433a-8bd5-4294-8a26-0898b7932d63
        URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(autorEntidade.getId())
        .toUri();

        return ResponseEntity.created(location).build();
    }
}
