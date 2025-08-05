package io.github.n4th05.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.github.n4th05.libraryapi.model.Autor;

public record AutorDTO(
        UUID id,
        String nome,
        LocalDate dataNascimento,
        String nacionalidade) {

            
    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
