package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Trazabilidad;
import com.pragma.powerup.infrastructure.out.jpa.entity.TrazabilidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITrazabilidadEntityMapper {
    TrazabilidadEntity toEntity(Trazabilidad trazabilidad);
    Trazabilidad toModel(TrazabilidadEntity trazabilidadEntity);
}
