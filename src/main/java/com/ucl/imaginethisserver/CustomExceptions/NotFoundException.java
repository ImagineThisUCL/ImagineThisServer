package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String resource) {
        super(resource + "Not Found.");
    }
    public NotFoundException() {
        super("Resource Not Found.");
    }
}
