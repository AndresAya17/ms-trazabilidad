package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.RankingEficienciaEmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import com.pragma.powerup.application.mapper.IEficienciaResponseMapper;
import com.pragma.powerup.application.mapper.IRankingEficienciaEmpleadoResponseMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadRequestMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadResponseMapper;
import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.model.Eficiencia;
import com.pragma.powerup.domain.model.RankingEficienciaEmpleado;
import com.pragma.powerup.domain.model.Trazabilidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrazabilidadHandler implements ITrazabilidadHandler {

    private final ITrazabilidadRequestMapper trazabilidadRequestMapper;
    private final ITrazabilidadResponseMapper trazabilidadResponseMapper;
    private final ITrazabilidadServicePort trazabilidadServicePort;
    private final IEficienciaResponseMapper eficienciaResponseMapper;
    private final IRankingEficienciaEmpleadoResponseMapper rankingEficienciaEmpleadoResponseMapper;


    @Override
    public void saveLogs(TrazabilidadRequestDto trazabilidadRequestDto) {
        Trazabilidad trz = trazabilidadRequestMapper.toDomain(trazabilidadRequestDto);
        trazabilidadServicePort.saveLogs(trz);
    }

    @Override
    public List<TrazabilidadResponseDto> getLogsByClientId(String clientId) {
        List<Trazabilidad> trazabilidadList = trazabilidadServicePort.getLogsByClientId(clientId);
        return trazabilidadResponseMapper.toResponse(trazabilidadList);
    }

    @Override
    public List<OrderEfficiencyResponseDto> getEfficiency(Long restaurantId) {
        List<Eficiencia> eficienciaList = trazabilidadServicePort.getEfficiency(restaurantId);
        return eficienciaResponseMapper.toResponse(eficienciaList);
    }

    @Override
    public List<RankingEficienciaEmpleadoResponseDto> getEmployeesEfficiencyRanking(Long restaurantId, Long userId) {
        List<RankingEficienciaEmpleado> rankingList =
                trazabilidadServicePort.getEmployeesEfficiencyRanking(restaurantId, userId);

        return rankingEficienciaEmpleadoResponseMapper.toResponse(rankingList);
    }
}
