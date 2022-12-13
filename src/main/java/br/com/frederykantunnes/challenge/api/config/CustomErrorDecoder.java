package br.com.frederykantunnes.challenge.api.config;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        //TODO: Ajustar Exceptions
        return switch (response.status()) {
            case 400 -> new RuntimeException("Bad Request");
            case 404 -> new RuntimeException("Not Found");
            default -> new Exception("Generic error");
        };
    }
}