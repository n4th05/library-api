package io.github.n4th05.libraryapi.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException{
    public OperacaoNaoPermitidaException(String message){
        super(message);
    }
}
