package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.exception.ErrorCode;
import com.pragma.powerup.domain.model.Trazabilidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class TrazabilidadDomainValidatorTest {

    @Test
    void shouldValidateSuccessfullyWhenTrazabilidadIsValid() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();

        assertDoesNotThrow(() -> TrazabilidadDomainValidator.validate(trazabilidad));
    }

    @Test
    void shouldThrowWhenTrazabilidadIsNull() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(null)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Traceability data must not be null", exception.getMessage());
    }

    @Test
    void shouldThrowWhenOrderIdIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setOrderId(null);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Order id is required and must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowWhenOrderIdIsZero() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setOrderId(0L);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Order id is required and must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowWhenOrderIdIsNegative() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setOrderId(-1L);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Order id is required and must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowWhenClientIdIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setClientId(null);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Client id is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenClientIdIsBlank() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setClientId("   ");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Client id is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenClientEmailIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setClientEmail(null);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Client email is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenClientEmailIsBlank() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setClientEmail("   ");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Client email is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenClientEmailIsInvalid() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setClientEmail("correo-invalido");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Client email must be valid", exception.getMessage());
    }

    @Test
    void shouldThrowWhenDateIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setDate(null);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Date is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenPreviousStatusIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setPreviousStatus(null);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Previous status is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenPreviousStatusIsBlank() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setPreviousStatus("   ");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Previous status is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenNewStatusIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setNewStatus(null);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("New status is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenNewStatusIsBlank() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setNewStatus("   ");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("New status is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenEmployeeIdIsZero() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setEmployeeId(0L);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Employee id must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowWhenEmployeeIdIsNegative() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setEmployeeId(-5L);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Employee id must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldNotThrowWhenEmployeeIdIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setEmployeeId(null);

        assertDoesNotThrow(() -> TrazabilidadDomainValidator.validate(trazabilidad));
    }

    @Test
    void shouldThrowWhenEmployeeEmailIsInvalid() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setEmployeeEmail("correo-invalido");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> TrazabilidadDomainValidator.validate(trazabilidad)
        );

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
        assertEquals("Employee email must be valid", exception.getMessage());
    }

    @Test
    void shouldNotThrowWhenEmployeeEmailIsNull() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setEmployeeEmail(null);

        assertDoesNotThrow(() -> TrazabilidadDomainValidator.validate(trazabilidad));
    }

    @Test
    void shouldNotThrowWhenEmployeeEmailIsBlank() {
        Trazabilidad trazabilidad = buildValidTrazabilidad();
        trazabilidad.setEmployeeEmail("   ");

        assertDoesNotThrow(() -> TrazabilidadDomainValidator.validate(trazabilidad));
    }

    private Trazabilidad buildValidTrazabilidad() {
        Trazabilidad trazabilidad = new Trazabilidad();
        trazabilidad.setOrderId(1L);
        trazabilidad.setClientId("client-123");
        trazabilidad.setClientEmail("cliente@test.com");
        trazabilidad.setDate(LocalDateTime.now());
        trazabilidad.setPreviousStatus("PENDIENTE");
        trazabilidad.setNewStatus("EN_PREPARACION");
        trazabilidad.setEmployeeId(10L);
        trazabilidad.setEmployeeEmail("empleado@test.com");
        return trazabilidad;
    }

}
