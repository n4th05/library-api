package io.github.n4th05.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.github.n4th05.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatorio") // Validação com Bean Validation. Recomendado usar para Strings.
        String nome,
        @NotNull(message = "campo obrigatorio") // mesmo efeito que o NotBlank, porém esse não é para Strings.
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatorio")
        String nacionalidade
    ) {

            
    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
