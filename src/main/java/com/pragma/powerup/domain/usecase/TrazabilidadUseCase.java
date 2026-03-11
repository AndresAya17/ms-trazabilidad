package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.model.Trazabilidad;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;

import java.util.List;

public class TrazabilidadUseCase implements ITrazabilidadServicePort {

    private final ITrazabilidadPersistencePort trazabilidadPersistencePort;

    public TrazabilidadUseCase(ITrazabilidadPersistencePort trazabilidadPersistencePort) {
        this.trazabilidadPersistencePort = trazabilidadPersistencePort;
    }

    @Override
    public void saveLogs(Trazabilidad trazabilidad) {
        //crear validator de modelo
        trazabilidadPersistencePort.saveLogs(trazabilidad);
    }

    @Override
    public List<Trazabilidad> getLogsByClientId(String clientId) {
        return  trazabilidadPersistencePort.getLogsByClientId(clientId);
    }
}
