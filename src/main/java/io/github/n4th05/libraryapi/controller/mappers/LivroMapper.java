package io.github.n4th05.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.n4th05.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.n4th05.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.n4th05.libraryapi.model.Livro;
import io.github.n4th05.libraryapi.repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class) // Trasforma ele em um componente do Spring, para que possa ser injetado em outros lugares.
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )") // target = entidade / source = DTO
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
