package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import com.pragma.powerup.application.mapper.ITrazabilidadRequestMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadResponseMapper;
import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
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
}
