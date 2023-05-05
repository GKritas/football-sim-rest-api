package com.gkritas.footballsimrestapi.exception;

public class SaveGameNotFoundException extends RuntimeException {
    public SaveGameNotFoundException(String message) {
        super(message);
    }
}
