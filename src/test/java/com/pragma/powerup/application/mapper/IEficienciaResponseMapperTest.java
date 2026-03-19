package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.domain.model.Eficiencia;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

class IEficienciaResponseMapperTest {

    // Instanciamos la implementación generada por MapStruct
    private final IEficienciaResponseMapper mapper = Mappers.getMapper(IEficienciaResponseMapper.class);

    @Test
    void shouldMapEficienciaListToOrderEfficiencyResponseDtoList() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().minusMinutes(30);
        LocalDateTime end = LocalDateTime.now();

        Eficiencia eficiencia = new Eficiencia(
                101L,         // orderId
                "client-123", // clientId
                50L,          // employeeId
                start,        // startDate
                end,          // endDate
                30L           // durationInMinutes
        );
        List<Eficiencia> eficienciaList = List.of(eficiencia);

        // Act
        List<OrderEfficiencyResponseDto> responseList = mapper.toResponse(eficienciaList);

        // Assert
        assertAll("Mapeo de Eficiencia a ResponseDto",
                () -> assertNotNull(responseList),
                () -> assertEquals(1, responseList.size()),
                () -> assertEquals(eficiencia.getOrderId(), responseList.get(0).getOrderId()),
                () -> assertEquals(eficiencia.getDurationInMinutes(), responseList.get(0).getDurationInMinutes())
        );
    }

    @Test
    void shouldReturnNullWhenEficienciaListIsNull() {
        // Act
        List<OrderEfficiencyResponseDto> result = mapper.toResponse(null);

        // Assert
        assertNull(result, "Si la entrada es nula, MapStruct debe retornar null");
    }

}
