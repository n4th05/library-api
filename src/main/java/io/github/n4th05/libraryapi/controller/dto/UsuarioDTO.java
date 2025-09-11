package io.github.n4th05.libraryapi.controller.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank (message = "Campo obrigatório")
        String login,
        @NotBlank (message = "Campo obrigatório")
        String senha,
        @NotBlank (message = "campo obrigatório")
        @Email (message = "Inválido")
        String email,
        List<String> roles) {

}
