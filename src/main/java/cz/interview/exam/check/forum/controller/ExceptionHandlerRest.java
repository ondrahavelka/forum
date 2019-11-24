package cz.interview.exam.check.forum.controller;

import cz.interview.exam.check.forum.model.RestApiErrorResponse;
import cz.interview.exam.check.forum.model.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerRest {

    public static ResponseEntity<RestApiErrorResponse> processException(Exception e) {
        RestApiErrorResponse restApiErrorResponse = new RestApiErrorResponse();
        restApiErrorResponse.setSignature("Exception occured, please try again.");
        restApiErrorResponse.setErrorText(e.getMessage());
        HttpStatus httpCode = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof UserNotFoundException) {
            httpCode = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(httpCode).body(restApiErrorResponse);
    }
}
