package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Trazabilidad;

import java.util.List;

public interface ITrazabilidadPersistencePort {
    void saveLogs(Trazabilidad trazabilidad);
    List<Trazabilidad> getLogsByClientId(String clientId);
    List<Trazabilidad> findByOrderIds(List<Long> orderIds);
}
