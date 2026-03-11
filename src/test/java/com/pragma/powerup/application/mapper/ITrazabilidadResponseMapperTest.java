package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.domain.model.Trazabilidad;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ITrazabilidadResponseMapperTest {

    private final ITrazabilidadResponseMapper mapper =
            Mappers.getMapper(ITrazabilidadResponseMapper.class);

    @Test
    void shouldMapDomainListToResponseListCorrectly() {
        LocalDateTime date1 = LocalDateTime.of(2026, 3, 11, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2026, 3, 11, 11, 0);

        Trazabilidad trazabilidad1 = new Trazabilidad();
        trazabilidad1.setOrderId(1L);
        trazabilidad1.setClientId("client-123");
        trazabilidad1.setClientEmail("cliente1@test.com");
        trazabilidad1.setDate(date1);
        trazabilidad1.setPreviousStatus("PENDIENTE");
        trazabilidad1.setNewStatus("EN_PREPARACION");
        trazabilidad1.setEmployeeId(10L);
        trazabilidad1.setEmployeeEmail("empleado1@test.com");

        Trazabilidad trazabilidad2 = new Trazabilidad();
        trazabilidad2.setOrderId(2L);
        trazabilidad2.setClientId("client-123");
        trazabilidad2.setClientEmail("cliente2@test.com");
        trazabilidad2.setDate(date2);
        trazabilidad2.setPreviousStatus("EN_PREPARACION");
        trazabilidad2.setNewStatus("LISTO");
        trazabilidad2.setEmployeeId(11L);
        trazabilidad2.setEmployeeEmail("empleado2@test.com");

        List<TrazabilidadResponseDto> result =
                mapper.toResponse(List.of(trazabilidad1, trazabilidad2));

        assertNotNull(result);
        assertEquals(2, result.size());

        TrazabilidadResponseDto first = result.get(0);
        assertEquals(1L, first.getOrderId());
        assertEquals("client-123", first.getClientId());
        assertEquals("cliente1@test.com", first.getClientEmail());
        assertEquals(date1, first.getDate());
        assertEquals("PENDIENTE", first.getPreviousStatus());
        assertEquals("EN_PREPARACION", first.getNewStatus());
        assertEquals(10L, first.getEmployeeId());
        assertEquals("empleado1@test.com", first.getEmployeeEmail());

        TrazabilidadResponseDto second = result.get(1);
        assertEquals(2L, second.getOrderId());
        assertEquals("client-123", second.getClientId());
        assertEquals("cliente2@test.com", second.getClientEmail());
        assertEquals(date2, second.getDate());
        assertEquals("EN_PREPARACION", second.getPreviousStatus());
        assertEquals("LISTO", second.getNewStatus());
        assertEquals(11L, second.getEmployeeId());
        assertEquals("empleado2@test.com", second.getEmployeeEmail());
    }

    @Test
    void shouldReturnEmptyListWhenDomainListIsEmpty() {
        List<TrazabilidadResponseDto> result = mapper.toResponse(Collections.emptyList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnNullWhenDomainListIsNull() {
        List<TrazabilidadResponseDto> result = mapper.toResponse(null);

        assertNull(result);
    }

}
