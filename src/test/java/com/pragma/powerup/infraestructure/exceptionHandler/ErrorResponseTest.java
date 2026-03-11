package com.pragma.powerup.infraestructure.exceptionHandler;

import com.pragma.powerup.infrastructure.exceptionhandler.ErrorResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {

    @Test
    void shouldCreateErrorResponseCorrectly() throws NoSuchFieldException, IllegalAccessException {
        String errorCode = "INVALID_DATA";
        String message = "Data is invalid";

        ErrorResponse errorResponse = new ErrorResponse(errorCode, message);

        Field errorCodeField = ErrorResponse.class.getDeclaredField("errorCode");
        errorCodeField.setAccessible(true);

        Field messageField = ErrorResponse.class.getDeclaredField("message");
        messageField.setAccessible(true);

        assertEquals(errorCode, errorCodeField.get(errorResponse));
        assertEquals(message, messageField.get(errorResponse));
    }

}
