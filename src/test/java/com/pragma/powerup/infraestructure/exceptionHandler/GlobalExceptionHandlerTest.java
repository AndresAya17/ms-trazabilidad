package com.pragma.powerup.infraestructure.exceptionHandler;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.exception.ErrorCode;
import com.pragma.powerup.infrastructure.exceptionhandler.ErrorResponse;
import com.pragma.powerup.infrastructure.exceptionhandler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleDomainExceptionWithBadRequestStatus() throws NoSuchFieldException, IllegalAccessException {
        DomainException exception = new DomainException(
                ErrorCode.INVALID_DATA,
                "Traceability data must not be null"
        );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleDomainException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse body = response.getBody();
        assertNotNull(body);

        Field errorCodeField = ErrorResponse.class.getDeclaredField("errorCode");
        errorCodeField.setAccessible(true);

        Field messageField = ErrorResponse.class.getDeclaredField("message");
        messageField.setAccessible(true);

        assertEquals("INVALID_DATA", errorCodeField.get(body));
        assertEquals("Traceability data must not be null", messageField.get(body));
    }

}
