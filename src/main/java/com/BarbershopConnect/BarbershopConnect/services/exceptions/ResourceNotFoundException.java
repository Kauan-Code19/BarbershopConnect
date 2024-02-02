package com.BarbershopConnect.BarbershopConnect.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException (String mensagem) {
        super(mensagem);
    }
}
