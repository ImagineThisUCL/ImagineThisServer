package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Resource Not Found")
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Resource Not Found");
    }
    public NotFoundException(String err) {
        super(err);
    }
}
