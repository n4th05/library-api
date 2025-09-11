package io.github.n4th05.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.n4th05.libraryapi.controller.dto.UsuarioDTO;
import io.github.n4th05.libraryapi.controller.mappers.UsuarioMapper;
import io.github.n4th05.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
