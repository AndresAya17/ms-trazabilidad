package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.domain.model.Trazabilidad;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ITrazabilidadRequestMapperTest {

    private final ITrazabilidadRequestMapper mapper =
            Mappers.getMapper(ITrazabilidadRequestMapper.class);

    @Test
    void shouldMapRequestDtoToDomainCorrectly() {
        LocalDateTime date = LocalDateTime.of(2026, 3, 11, 10, 0);

        TrazabilidadRequestDto requestDto = new TrazabilidadRequestDto();
        requestDto.setOrderId(1L);
        requestDto.setClientId("client-123");
        requestDto.setClientEmail("cliente@test.com");
        requestDto.setDate(date);
        requestDto.setPreviousStatus("PENDIENTE");
        requestDto.setNewStatus("EN_PREPARACION");
        requestDto.setEmployeeId(10L);
        requestDto.setEmployeeEmail("empleado@test.com");

        Trazabilidad result = mapper.toDomain(requestDto);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals("client-123", result.getClientId());
        assertEquals("cliente@test.com", result.getClientEmail());
        assertEquals(date, result.getDate());
        assertEquals("PENDIENTE", result.getPreviousStatus());
        assertEquals("EN_PREPARACION", result.getNewStatus());
        assertEquals(10L, result.getEmployeeId());
        assertEquals("empleado@test.com", result.getEmployeeEmail());
    }

    @Test
    void shouldMapNullRequestDtoToNull() {
        Trazabilidad result = mapper.toDomain(null);

        assertNull(result);
    }

    @Test
    void shouldMapRequestDtoWithNullOptionalFields() {
        LocalDateTime date = LocalDateTime.of(2026, 3, 11, 10, 0);

        TrazabilidadRequestDto requestDto = new TrazabilidadRequestDto();
        requestDto.setOrderId(1L);
        requestDto.setClientId("client-123");
        requestDto.setClientEmail("cliente@test.com");
        requestDto.setDate(date);
        requestDto.setPreviousStatus("PENDIENTE");
        requestDto.setNewStatus("EN_PREPARACION");
        requestDto.setEmployeeId(null);
        requestDto.setEmployeeEmail(null);

        Trazabilidad result = mapper.toDomain(requestDto);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals("client-123", result.getClientId());
        assertEquals("cliente@test.com", result.getClientEmail());
        assertEquals(date, result.getDate());
        assertEquals("PENDIENTE", result.getPreviousStatus());
        assertEquals("EN_PREPARACION", result.getNewStatus());
        assertNull(result.getEmployeeId());
        assertNull(result.getEmployeeEmail());
    }

}
