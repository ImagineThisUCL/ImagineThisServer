package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal error")
public class InternalError extends RuntimeException {
    public InternalError(String err) {
        super(err);
    }
    public InternalError() {
        super("Internal Server Error.");
    }
}
