package io.github.n4th05.libraryapi.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.n4th05.libraryapi.controller.dto.ErroCampo;
import io.github.n4th05.libraryapi.controller.dto.ErroResposta;
import io.github.n4th05.libraryapi.exceptions.CampoInvalidoException;
import io.github.n4th05.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.n4th05.libraryapi.exceptions.RegistroDuplicadoException;

@RestControllerAdvice // Ele captura Exception.
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class) // Ele captura o erro e retorna uma resposta customizada.
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Serve para mapear o retorno desse método. Ou seja, ele vai ser sempre UNPROCESSABLE_ENTITY.
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação!",
                listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(
            OperacaoNaoPermitidaException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação!",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e){
        return new ErroResposta(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Ocorreu um Erro inesperado. Entre em contato com a administracao. ",
            List.of());
    }
}
