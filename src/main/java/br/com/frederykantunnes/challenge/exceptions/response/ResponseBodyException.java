package br.com.frederykantunnes.challenge.exceptions.response;

import org.springframework.http.HttpStatus;
import java.util.Date;

public class ResponseBodyException {

    private HttpStatus status;
    private String message;
    private ErrorCodeEnum errorCode;
    private Date timestamp;

    public ResponseBodyException(HttpStatus status, String message, ErrorCodeEnum errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = new Date();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
