package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
class TrazabilidadTest {

    @Test
    void shouldSetAndGetAllFieldsCorrectly() {
        Trazabilidad trazabilidad = new Trazabilidad();
        LocalDateTime date = LocalDateTime.now();

        trazabilidad.setOrderId(1L);
        trazabilidad.setClientId("client-123");
        trazabilidad.setClientEmail("cliente@test.com");
        trazabilidad.setDate(date);
        trazabilidad.setPreviousStatus("PENDIENTE");
        trazabilidad.setNewStatus("EN_PREPARACION");
        trazabilidad.setEmployeeId(10L);
        trazabilidad.setEmployeeEmail("empleado@test.com");

        assertEquals(1L, trazabilidad.getOrderId());
        assertEquals("client-123", trazabilidad.getClientId());
        assertEquals("cliente@test.com", trazabilidad.getClientEmail());
        assertEquals(date, trazabilidad.getDate());
        assertEquals("PENDIENTE", trazabilidad.getPreviousStatus());
        assertEquals("EN_PREPARACION", trazabilidad.getNewStatus());
        assertEquals(10L, trazabilidad.getEmployeeId());
        assertEquals("empleado@test.com", trazabilidad.getEmployeeEmail());
    }

    @Test
    void shouldHaveNullFieldsByDefault() {
        Trazabilidad trazabilidad = new Trazabilidad();

        assertNull(trazabilidad.getOrderId());
        assertNull(trazabilidad.getClientId());
        assertNull(trazabilidad.getClientEmail());
        assertNull(trazabilidad.getDate());
        assertNull(trazabilidad.getPreviousStatus());
        assertNull(trazabilidad.getNewStatus());
        assertNull(trazabilidad.getEmployeeId());
        assertNull(trazabilidad.getEmployeeEmail());
    }
}
