package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Trazabilidad;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ITrazabilidadEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ITrazabilidadRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrazabilidadJpaAdapter implements ITrazabilidadPersistencePort {

    private final ITrazabilidadEntityMapper trazabilidadEntityMapper;
    private final ITrazabilidadRepository trazabilidadRepository;

    @Override
    public void saveLogs(Trazabilidad trazabilidad) {
        trazabilidadRepository.save(trazabilidadEntityMapper.toEntity(trazabilidad));
    }

    @Override
    public List<Trazabilidad> getLogsByClientId(String clientId) {
        return trazabilidadRepository.findAllByClientIdOrderByDateDesc(clientId)
                .stream()
                .map(trazabilidadEntityMapper::toModel)
                .toList();
    }
}
