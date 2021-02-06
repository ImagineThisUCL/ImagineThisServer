package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request.")
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad Request");
    }

    public BadRequestException(String err) {
        super(err);
    }
}
