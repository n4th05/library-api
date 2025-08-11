package io.github.n4th05.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.n4th05.libraryapi.controller.dto.AutorDTO;
import io.github.n4th05.libraryapi.model.Autor;

@Mapper(componentModel = "spring") // Trasforma ele em um componente do Spring, para que possa ser injetado em outros lugares.
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome") // Mapeia o campo nome do DTO para o campo nome da entidade Autor.
    @Mapping(source = "dataNascimento", target = "dataNascimento") // Mapeia o campo dataNascimento do DTO para o campo dataNascimento da entidade Autor.
    @Mapping(source = "nacionalidade", target = "nacionalidade") // Mapeia o campo nacionalidade do DTO para o campo nacionalidade da entidade Autor.
    
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);

}
