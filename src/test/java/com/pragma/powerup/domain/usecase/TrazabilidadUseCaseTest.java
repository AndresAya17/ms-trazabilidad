package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Trazabilidad;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrazabilidadUseCaseTest {

    @Mock
    private ITrazabilidadPersistencePort trazabilidadPersistencePort;

    @InjectMocks
    private TrazabilidadUseCase trazabilidadUseCase;

    private Trazabilidad trazabilidad;

    @BeforeEach
    void setUp() {
        trazabilidad = new Trazabilidad();
        trazabilidad.setOrderId(1L);
        trazabilidad.setClientId("client-123");
        trazabilidad.setClientEmail("cliente@test.com");
        trazabilidad.setDate(LocalDateTime.now());
        trazabilidad.setPreviousStatus("PENDIENTE");
        trazabilidad.setNewStatus("EN_PREPARACION");
        trazabilidad.setEmployeeId(10L);
        trazabilidad.setEmployeeEmail("empleado@test.com");
    }

    @Test
    void shouldSaveLogsSuccessfullyWhenTrazabilidadIsValid() {
        assertDoesNotThrow(() -> trazabilidadUseCase.saveLogs(trazabilidad));

        verify(trazabilidadPersistencePort).saveLogs(trazabilidad);
    }

    @Test
    void shouldThrowExceptionWhenSavingLogsWithInvalidTrazabilidad() {
        trazabilidad.setOrderId(null);

        assertThrows(DomainException.class, () -> trazabilidadUseCase.saveLogs(trazabilidad));

        verifyNoInteractions(trazabilidadPersistencePort);
    }

    @Test
    void shouldReturnLogsByClientId() {
        String clientId = "client-123";

        Trazabilidad trazabilidad1 = new Trazabilidad();
        trazabilidad1.setOrderId(1L);
        trazabilidad1.setClientId(clientId);
        trazabilidad1.setClientEmail("cliente@test.com");
        trazabilidad1.setDate(LocalDateTime.now());
        trazabilidad1.setPreviousStatus("PENDIENTE");
        trazabilidad1.setNewStatus("EN_PREPARACION");

        Trazabilidad trazabilidad2 = new Trazabilidad();
        trazabilidad2.setOrderId(2L);
        trazabilidad2.setClientId(clientId);
        trazabilidad2.setClientEmail("cliente@test.com");
        trazabilidad2.setDate(LocalDateTime.now());
        trazabilidad2.setPreviousStatus("EN_PREPARACION");
        trazabilidad2.setNewStatus("LISTO");

        List<Trazabilidad> expectedLogs = List.of(trazabilidad1, trazabilidad2);

        when(trazabilidadPersistencePort.getLogsByClientId(clientId))
                .thenReturn(expectedLogs);

        List<Trazabilidad> result = trazabilidadUseCase.getLogsByClientId(clientId);

        assertEquals(2, result.size());
        assertEquals(expectedLogs, result);

        verify(trazabilidadPersistencePort).getLogsByClientId(clientId);
    }

}
