package io.github.n4th05.libraryapi.controller.dto;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {

}
