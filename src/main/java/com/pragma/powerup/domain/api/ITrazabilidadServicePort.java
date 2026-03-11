package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Trazabilidad;

import java.util.List;

public interface ITrazabilidadServicePort {
    void saveLogs(Trazabilidad trazabilidad);
    List<Trazabilidad> getLogsByClientId(String clientId);
}
