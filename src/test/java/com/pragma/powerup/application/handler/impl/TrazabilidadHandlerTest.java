package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.mapper.ITrazabilidadRequestMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadResponseMapper;
import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.model.Trazabilidad;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadHandlerTest {

    @Mock
    private ITrazabilidadRequestMapper trazabilidadRequestMapper;

    @Mock
    private ITrazabilidadResponseMapper trazabilidadResponseMapper;

    @Mock
    private ITrazabilidadServicePort trazabilidadServicePort;

    @InjectMocks
    private TrazabilidadHandler trazabilidadHandler;

    @Test
    void shouldSaveLogsSuccessfully() {
        TrazabilidadRequestDto requestDto = new TrazabilidadRequestDto();
        requestDto.setOrderId(1L);
        requestDto.setClientId("client-123");
        requestDto.setClientEmail("cliente@test.com");
        requestDto.setDate(LocalDateTime.now());
        requestDto.setPreviousStatus("PENDIENTE");
        requestDto.setNewStatus("EN_PREPARACION");
        requestDto.setEmployeeId(10L);
        requestDto.setEmployeeEmail("empleado@test.com");

        Trazabilidad trazabilidad = new Trazabilidad();
        trazabilidad.setOrderId(1L);
        trazabilidad.setClientId("client-123");

        when(trazabilidadRequestMapper.toDomain(requestDto)).thenReturn(trazabilidad);

        trazabilidadHandler.saveLogs(requestDto);

        verify(trazabilidadRequestMapper).toDomain(requestDto);
        verify(trazabilidadServicePort).saveLogs(trazabilidad);
    }

    @Test
    void shouldGetLogsByClientIdSuccessfully() {
        String clientId = "client-123";

        Trazabilidad trazabilidad1 = new Trazabilidad();
        trazabilidad1.setOrderId(1L);
        trazabilidad1.setClientId(clientId);

        Trazabilidad trazabilidad2 = new Trazabilidad();
        trazabilidad2.setOrderId(2L);
        trazabilidad2.setClientId(clientId);

        List<Trazabilidad> trazabilidadList = List.of(trazabilidad1, trazabilidad2);

        TrazabilidadResponseDto responseDto1 = new TrazabilidadResponseDto();
        responseDto1.setOrderId(1L);
        responseDto1.setClientId(clientId);

        TrazabilidadResponseDto responseDto2 = new TrazabilidadResponseDto();
        responseDto2.setOrderId(2L);
        responseDto2.setClientId(clientId);

        List<TrazabilidadResponseDto> responseList = List.of(responseDto1, responseDto2);

        when(trazabilidadServicePort.getLogsByClientId(clientId)).thenReturn(trazabilidadList);
        when(trazabilidadResponseMapper.toResponse(trazabilidadList)).thenReturn(responseList);

        List<TrazabilidadResponseDto> result = trazabilidadHandler.getLogsByClientId(clientId);

        verify(trazabilidadServicePort).getLogsByClientId(clientId);
        verify(trazabilidadResponseMapper).toResponse(trazabilidadList);

        assertEquals(2, result.size());
        assertSame(responseDto1, result.get(0));
        assertSame(responseDto2, result.get(1));
    }

}
