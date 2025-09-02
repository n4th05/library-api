package io.github.n4th05.libraryapi.controller.mappers;

import org.mapstruct.Mapper;

import io.github.n4th05.libraryapi.controller.dto.UsuarioDTO;
import io.github.n4th05.libraryapi.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
