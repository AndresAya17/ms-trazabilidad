package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.RankingEficienciaEmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.mapper.IEficienciaResponseMapper;
import com.pragma.powerup.application.mapper.IRankingEficienciaEmpleadoResponseMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadRequestMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadResponseMapper;
import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.model.Eficiencia;
import com.pragma.powerup.domain.model.RankingEficienciaEmpleado;
import com.pragma.powerup.domain.model.Trazabilidad;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadHandlerTest {

    @Mock
    private ITrazabilidadRequestMapper trazabilidadRequestMapper;

    @Mock
    private ITrazabilidadResponseMapper trazabilidadResponseMapper;

    @Mock
    private IEficienciaResponseMapper eficienciaResponseMapper;

    @Mock
    private IRankingEficienciaEmpleadoResponseMapper rankingEficienciaEmpleadoResponseMapper;

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
    @Test
    void shouldGetEfficiencySuccessfully() {
        // Arrange
        Long restaurantId = 1L;

        Eficiencia eficiencia = new Eficiencia(100L, "client-1", 10L, LocalDateTime.now(), LocalDateTime.now(), 30L);
        List<Eficiencia> eficienciaList = List.of(eficiencia);

        OrderEfficiencyResponseDto responseDto = new OrderEfficiencyResponseDto();
        responseDto.setOrderId(100L);
        responseDto.setDurationInMinutes(30L);
        List<OrderEfficiencyResponseDto> responseList = List.of(responseDto);

        when(trazabilidadServicePort.getEfficiency(restaurantId)).thenReturn(eficienciaList);
        when(eficienciaResponseMapper.toResponse(eficienciaList)).thenReturn(responseList);

        // Act
        List<OrderEfficiencyResponseDto> result = trazabilidadHandler.getEfficiency(restaurantId);

        // Assert
        assertAll("Verificación de Eficiencia por Orden",
                () -> assertEquals(1, result.size()),
                () -> assertEquals(100L, result.get(0).getOrderId()),
                () -> verify(trazabilidadServicePort).getEfficiency(restaurantId),
                () -> verify(eficienciaResponseMapper).toResponse(eficienciaList)
        );
    }
    @Test
    void shouldGetEmployeesEfficiencyRankingSuccessfully() {
        // Arrange
        Long restaurantId = 1L;
        Long userId = 5L;

        RankingEficienciaEmpleado ranking = new RankingEficienciaEmpleado(10L, 5L, 25L);
        List<RankingEficienciaEmpleado> rankingList = List.of(ranking);

        RankingEficienciaEmpleadoResponseDto responseDto = new RankingEficienciaEmpleadoResponseDto();
        responseDto.setEmployeeId(10L);
        responseDto.setOrdersCount(5L);
        responseDto.setAverageDurationInMinutes(25L);
        List<RankingEficienciaEmpleadoResponseDto> responseList = List.of(responseDto);

        when(trazabilidadServicePort.getEmployeesEfficiencyRanking(restaurantId, userId)).thenReturn(rankingList);
        when(rankingEficienciaEmpleadoResponseMapper.toResponse(rankingList)).thenReturn(responseList);

        // Act
        List<RankingEficienciaEmpleadoResponseDto> result = trazabilidadHandler.getEmployeesEfficiencyRanking(restaurantId, userId);

        // Assert
        assertAll("Verificación de Ranking de Empleados",
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(10L, result.get(0).getEmployeeId()),
                () -> assertEquals(5L, result.get(0).getOrdersCount()),
                () -> verify(trazabilidadServicePort).getEmployeesEfficiencyRanking(restaurantId, userId),
                () -> verify(rankingEficienciaEmpleadoResponseMapper).toResponse(rankingList)
        );
    }

}
