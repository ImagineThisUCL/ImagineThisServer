package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FigmaDesignException extends RuntimeException {
    public FigmaDesignException() {
        super("There was an error in your Figma design.");
    }
    public FigmaDesignException(String err) { super(err); }
}
