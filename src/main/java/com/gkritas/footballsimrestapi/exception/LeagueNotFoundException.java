package com.gkritas.footballsimrestapi.exception;

public class LeagueNotFoundException extends RuntimeException{
    public LeagueNotFoundException(String message) {
        super(message);
    }
}
