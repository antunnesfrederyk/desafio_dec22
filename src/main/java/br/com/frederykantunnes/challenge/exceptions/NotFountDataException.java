package br.com.frederykantunnes.challenge.exceptions;


public class NotFountDataException extends RuntimeException {
    public NotFountDataException(String reason) {
        super(reason);
    }

}
