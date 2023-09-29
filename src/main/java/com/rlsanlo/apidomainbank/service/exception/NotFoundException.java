package com.rlsanlo.apidomainbank.service.exception;

public class NotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotFoundException(){
        super("Resource not found.");
    }
}
