package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RankingEficienciaEmpleadoResponseDto {
    private Long employeeId;
    private Long ordersCount;
    private Long averageDurationInMinutes;
}
