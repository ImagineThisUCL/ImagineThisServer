package com.ucl.imaginethisserver.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Feedback Not Found")
public class FeedbackNotFoundException extends NotFoundException {
    public FeedbackNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
