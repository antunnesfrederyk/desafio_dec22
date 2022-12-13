package br.com.frederykantunnes.challenge.exceptions;


import br.com.frederykantunnes.challenge.exceptions.response.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class DuplicatedException extends RuntimeException {

    private HttpStatus status;
    private ErrorCodeEnum errorCode;

    public DuplicatedException(HttpStatus status, String reason, ErrorCodeEnum errorCode) {
        super(reason);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }
}
