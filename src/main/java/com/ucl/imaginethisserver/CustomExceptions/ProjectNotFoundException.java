package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Project Not Found")
public class ProjectNotFoundException extends NotFoundException {
    public ProjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
