package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Project Not Found")
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
