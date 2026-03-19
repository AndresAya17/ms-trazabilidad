package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RankingEficienciaEmpleadoResponseDto;
import com.pragma.powerup.domain.model.RankingEficienciaEmpleado;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRankingEficienciaEmpleadoResponseMapper {
    List<RankingEficienciaEmpleadoResponseDto> toResponse(List<RankingEficienciaEmpleado> rankingEficienciaEmpleados);
}
