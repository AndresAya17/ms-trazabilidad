package com.pragma.powerup.domain.api;

import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.domain.model.Eficiencia;
import com.pragma.powerup.domain.model.RankingEficienciaEmpleado;
import com.pragma.powerup.domain.model.Trazabilidad;

import java.util.List;

public interface ITrazabilidadServicePort {
    void saveLogs(Trazabilidad trazabilidad);
    List<Trazabilidad> getLogsByClientId(String clientId);
    List<Eficiencia> getEfficiency(Long restaurantId);
    List<RankingEficienciaEmpleado> getEmployeesEfficiencyRanking(Long restaurantId, Long userId);
}
