package io.github.n4th05.libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.n4th05.libraryapi.model.Client;
import io.github.n4th05.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
@Slf4j
public class ClientController {

    private final ClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo Client.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Client cadastrado com sucesso."),
        @ApiResponse(responseCode = "422", description = "Erro de validação."),
        @ApiResponse(responseCode = "409", description = "Client já cadastrado.")
    }
    )
    public void salvar(@RequestBody Client client){
        log.info("Registrando novo Client: {} com scope: {}", client.getClientId(), client.getScope());
        
        service.salvar(client);
    }
}
