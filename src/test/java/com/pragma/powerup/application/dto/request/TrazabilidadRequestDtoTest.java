package com.pragma.powerup.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrazabilidadRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldSetAndGetAllFieldsCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        TrazabilidadRequestDto dto = new TrazabilidadRequestDto();
        dto.setOrderId(1L);
        dto.setClientId("client-123");
        dto.setClientEmail("cliente@test.com");
        dto.setDate(now);
        dto.setPreviousStatus("PENDIENTE");
        dto.setNewStatus("EN_PREPARACION");
        dto.setEmployeeId(10L);
        dto.setEmployeeEmail("empleado@test.com");

        assertEquals(1L, dto.getOrderId());
        assertEquals("client-123", dto.getClientId());
        assertEquals("cliente@test.com", dto.getClientEmail());
        assertEquals(now, dto.getDate());
        assertEquals("PENDIENTE", dto.getPreviousStatus());
        assertEquals("EN_PREPARACION", dto.getNewStatus());
        assertEquals(10L, dto.getEmployeeId());
        assertEquals("empleado@test.com", dto.getEmployeeEmail());
    }

    @Test
    void shouldHaveNoViolationsWhenDtoIsValid() {
        TrazabilidadRequestDto dto = buildValidDto();

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenOrderIdIsNull() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setOrderId(null);

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Order id is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenClientIdIsNull() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setClientId(null);

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Customer id is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenClientIdIsBlank() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setClientId("   ");

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Customer id is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenClientEmailIsNull() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setClientEmail(null);

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Customer email is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenClientEmailIsBlank() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setClientEmail("   ");

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailWhenClientEmailIsInvalid() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setClientEmail("correo-invalido");

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Customer email must be valid", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenDateIsNull() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setDate(null);

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Date is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenPreviousStatusIsNull() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setPreviousStatus(null);

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Previous status is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenPreviousStatusIsBlank() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setPreviousStatus("   ");

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Previous status is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenNewStatusIsNull() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setNewStatus(null);

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("New status is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenNewStatusIsBlank() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setNewStatus("   ");

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("New status is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenEmployeeEmailIsInvalid() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setEmployeeEmail("correo-invalido");

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Employee email must be valid", violations.iterator().next().getMessage());
    }

    @Test
    void shouldNotFailWhenEmployeeEmailIsNull() {
        TrazabilidadRequestDto dto = buildValidDto();
        dto.setEmployeeEmail(null);

        Set<ConstraintViolation<TrazabilidadRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    private TrazabilidadRequestDto buildValidDto() {
        TrazabilidadRequestDto dto = new TrazabilidadRequestDto();
        dto.setOrderId(1L);
        dto.setClientId("client-123");
        dto.setClientEmail("cliente@test.com");
        dto.setDate(LocalDateTime.now());
        dto.setPreviousStatus("PENDIENTE");
        dto.setNewStatus("EN_PREPARACION");
        dto.setEmployeeId(10L);
        dto.setEmployeeEmail("empleado@test.com");
        return dto;
    }

}
