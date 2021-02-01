package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Update Not Successful")
public class UpdateException extends RuntimeException {
    public UpdateException(String errorMessage) {
        super(errorMessage);
    }
}