package io.github.n4th05.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import io.github.n4th05.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "ISBN Inválido!")
        String isbn,
        @NotBlank(message = "Campo obrigatório!")
        String titulo,
        @NotNull(message = "Campo obrigatório!")
        @Past(message = "Não pode ser uma data futura!")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "Campo obrigatório!")
        UUID idAutor
        ) {
    
}
