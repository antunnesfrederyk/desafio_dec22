package br.com.frederykantunnes.challenge.api.config;

import br.com.frederykantunnes.challenge.exceptions.BadRequestException;
import br.com.frederykantunnes.challenge.exceptions.NotFountDataException;
import br.com.frederykantunnes.challenge.exceptions.response.ErrorCodeEnum;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> throw new BadRequestException(HttpStatus.valueOf(response.status()), response.reason(), ErrorCodeEnum.E0001);
            case 404 -> throw new NotFountDataException("Document not found!");
            default -> new BadRequestException(HttpStatus.UNPROCESSABLE_ENTITY, "Generic Error Api", ErrorCodeEnum.E0001);
        };
    }
}