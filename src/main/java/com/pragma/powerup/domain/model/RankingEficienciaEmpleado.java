package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RankingEficienciaEmpleado {

    private Long employeeId;
    private Long ordersCount;
    private Long averageDurationInMinutes;

}
