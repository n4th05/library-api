package io.github.n4th05.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatorio") // Validação com Bean Validation. Recomendado usar para Strings.
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrão!") // Serve para validar o tamanho do campo.
        @Schema(name = "nome")
        String nome,
        @NotNull(message = "Campo obrigatorio!") // mesmo efeito que o NotBlank, porém esse não é para Strings.
        @Past(message = "Não pode ser uma data futura!") // Validação para datas. Ou seja, a data de nascimento deve ser sempre no passado.
        @Schema(name = "dataNascimento")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio!")
        @Size(max = 50, min = 2 , message = "Tamanho deve ser entre 2 e 50 caracteres") // Validação do tamanho do campo.
        @Schema(name = "nacionalidade")
        String nacionalidade
    ) {  

    }
