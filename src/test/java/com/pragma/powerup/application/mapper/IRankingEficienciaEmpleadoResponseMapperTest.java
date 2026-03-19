package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RankingEficienciaEmpleadoResponseDto;
import com.pragma.powerup.domain.model.RankingEficienciaEmpleado;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

class IRankingEficienciaEmpleadoResponseMapperTest {

    // Instanciamos la implementación generada por MapStruct
    private final IRankingEficienciaEmpleadoResponseMapper mapper = Mappers.getMapper(IRankingEficienciaEmpleadoResponseMapper.class);

    @Test
    void shouldMapRankingListToResponseDtoList() {
        // Arrange
        RankingEficienciaEmpleado ranking = new RankingEficienciaEmpleado(
                50L,   // employeeId
                10L,  // ordersCount
                25L   // averageDurationInMinutes
        );
        List<RankingEficienciaEmpleado> rankingList = List.of(ranking);

        // Act
        List<RankingEficienciaEmpleadoResponseDto> responseList = mapper.toResponse(rankingList);

        // Assert
        assertAll
                ("Mapeo de Ranking a DTO",
                () -> assertNotNull(responseList),
                () -> assertEquals(1, responseList.size()),
                () -> assertEquals(ranking.getEmployeeId(), responseList.get(0).getEmployeeId()),
                () -> assertEquals(ranking.getOrdersCount(), responseList.get(0).getOrdersCount()),
                () -> assertEquals(ranking.getAverageDurationInMinutes(), responseList.get(0).getAverageDurationInMinutes())
        );
    }

    @Test
    void shouldReturnNullWhenSourceListIsNull() {
        // Act
        List<RankingEficienciaEmpleadoResponseDto> result = mapper.toResponse(null);

        // Assert
        assertNull(result);
    }
}
