package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankingEficienciaEmpleadoResponseDto {
    private Long employeeId;
    private Long ordersCount;
    private Long averageDurationInMinutes;
}
