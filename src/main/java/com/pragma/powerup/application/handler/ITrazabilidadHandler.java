package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;

import java.util.List;

public interface ITrazabilidadHandler {

    void saveLogs(TrazabilidadRequestDto trazabilidadRequestDto);

    List<TrazabilidadResponseDto> getLogsByClientId(String clientId);
}
