package br.com.frederykantunnes.challenge.exceptions.handler;

import br.com.frederykantunnes.challenge.exceptions.BadRequestException;
import br.com.frederykantunnes.challenge.exceptions.DuplicatedException;
import br.com.frederykantunnes.challenge.exceptions.NotFountDataException;
import br.com.frederykantunnes.challenge.exceptions.response.ErrorCodeEnum;
import br.com.frederykantunnes.challenge.exceptions.response.ResponseBodyException;
import br.com.frederykantunnes.challenge.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFountDataException.class)
    public ResponseEntity<ResponseBodyException> handle(NotFountDataException exception){
        log.error("Error: {}", JsonUtils.toJson(exception.getMessage()));
        return new ResponseEntity<>(new ResponseBodyException(HttpStatus.NOT_FOUND, exception.getMessage(), ErrorCodeEnum.E0001), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseBodyException> handle(BadRequestException exception){
        log.error("Error: {}", JsonUtils.toJson(exception.getMessage()));
        ResponseBodyException build = new ResponseBodyException(exception.getStatus(), exception.getMessage(), exception.getErrorCode());
        return new ResponseEntity<>(build, exception.getStatus());
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ResponseBodyException> handle(DuplicatedException exception){
        log.error("Error: {}", JsonUtils.toJson(exception.getMessage()));
        ResponseBodyException build = new ResponseBodyException(exception.getStatus(), exception.getMessage(), exception.getErrorCode());
        return new ResponseEntity<>(build, exception.getStatus());
    }

}
