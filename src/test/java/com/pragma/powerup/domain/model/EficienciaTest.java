package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class EficienciaTest {

    @Test
    void testEficienciaConstructorAndGetters() {
        // Arrange (Preparar datos)
        Long orderId = 1L;
        String clientId = "client-123";
        Long employeeId = 10L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMinutes(30);
        Long duration = 30L;

        // Act (Ejecutar constructor)
        Eficiencia eficiencia = new Eficiencia(orderId, clientId, employeeId, startDate, endDate, duration);

        // Assert (Verificar getters)
        assertEquals(orderId, eficiencia.getOrderId());
        assertEquals(clientId, eficiencia.getClientId());
        assertEquals(employeeId, eficiencia.getEmployeeId());
        assertEquals(startDate, eficiencia.getStartDate());
        assertEquals(endDate, eficiencia.getEndDate());
        assertEquals(duration, eficiencia.getDurationInMinutes());
    }

    @Test
    void testEficienciaSetters() {
        // Arrange
        Eficiencia eficiencia = new Eficiencia(null, null, null, null, null, null);
        Long orderId = 2L;
        String clientId = "client-456";
        Long employeeId = 20L;
        LocalDateTime startDate = LocalDateTime.of(2026, 3, 11, 10, 0);
        LocalDateTime endDate = startDate.plusHours(1);
        Long duration = 60L;

        // Act (Usar setters)
        eficiencia.setOrderId(orderId);
        eficiencia.setClientId(clientId);
        eficiencia.setEmployeeId(employeeId);
        eficiencia.setStartDate(startDate);
        eficiencia.setEndDate(endDate);
        eficiencia.setDurationInMinutes(duration);

        // Assert
        assertAll("Verificación de todos los setters",
                () -> assertEquals(orderId, eficiencia.getOrderId()),
                () -> assertEquals(clientId, eficiencia.getClientId()),
                () -> assertEquals(employeeId, eficiencia.getEmployeeId()),
                () -> assertEquals(startDate, eficiencia.getStartDate()),
                () -> assertEquals(endDate, eficiencia.getEndDate()),
                () -> assertEquals(duration, eficiencia.getDurationInMinutes())
        );
    }

}
