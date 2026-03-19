package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.domain.model.Eficiencia;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEficienciaResponseMapper {
    List<OrderEfficiencyResponseDto> toResponse(List<Eficiencia> eficienciaList);
}
