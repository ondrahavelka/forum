package cz.interview.exam.check.forum;

import cz.interview.exam.check.forum.controller.ExceptionHandlerRest;
import cz.interview.exam.check.forum.model.RestApiErrorResponse;
import cz.interview.exam.check.forum.model.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionHandlerRestTest {

    @Test
    void processExceptionUserNotFoundTest() {
        RestApiErrorResponse expectedBody = createErrorResponse("Exception occured, please try again.", "User id 145 and his posts was not found");
        ResponseEntity<RestApiErrorResponse> expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(expectedBody);
        ResponseEntity<RestApiErrorResponse> actualResponseEntity = ExceptionHandlerRest.processException(new UserNotFoundException("User id 145 and his posts was not found"));
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void processExceptionGeneralErrorTest() {
        RestApiErrorResponse expectedBody = createErrorResponse("Exception occured, please try again.", "Network problem occured");
        ResponseEntity<RestApiErrorResponse> expectedResponseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(expectedBody);
        ResponseEntity<RestApiErrorResponse> actualResponseEntity = ExceptionHandlerRest.processException(new IOException("Network problem occured"));
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    private RestApiErrorResponse createErrorResponse(String signature, String errorText) {
        RestApiErrorResponse restApiErrorResponse = new RestApiErrorResponse();
        restApiErrorResponse.setSignature(signature);
        restApiErrorResponse.setErrorText(errorText);
        return restApiErrorResponse;
    }
}
