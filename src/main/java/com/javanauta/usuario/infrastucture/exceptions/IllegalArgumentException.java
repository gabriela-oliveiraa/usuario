package com.javanauta.usuario.infrastucture.exceptions;

public class IllegalArgumentException extends RuntimeException{

    public IllegalArgumentException(String mensagem){
        super(mensagem);
    }

    public IllegalArgumentException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
