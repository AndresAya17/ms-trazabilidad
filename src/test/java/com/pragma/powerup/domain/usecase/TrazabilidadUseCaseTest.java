package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Eficiencia;
import com.pragma.powerup.domain.model.RankingEficienciaEmpleado;
import com.pragma.powerup.domain.model.Trazabilidad;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadUseCaseTest {

    @Mock
    private ITrazabilidadPersistencePort trazabilidadPersistencePort;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

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
    @Test
    void shouldReturnEmptyListWhenNoOrdersFoundForRestaurant() {
        Long restaurantId = 1L;

        // Simulamos que no hay órdenes para ese restaurante
        when(orderPersistencePort.getOrdersByRestaurantId(restaurantId)).thenReturn(List.of());

        List<Eficiencia> result = trazabilidadUseCase.getEfficiency(restaurantId);

        assertTrue(result.isEmpty());
        verify(orderPersistencePort).getOrdersByRestaurantId(restaurantId);
        verifyNoInteractions(trazabilidadPersistencePort);
    }

    @Test
    void shouldReturnEmptyListWhenOrdersHaveNoTrazabilidad() {
        Long restaurantId = 1L;
        List<Long> orderIds = List.of(1L, 2L);

        when(orderPersistencePort.getOrdersByRestaurantId(restaurantId)).thenReturn(orderIds);
        // Simulamos que las órdenes existen pero no tienen registros de trazabilidad
        when(trazabilidadPersistencePort.findByOrderIds(orderIds)).thenReturn(List.of());

        List<Eficiencia> result = trazabilidadUseCase.getEfficiency(restaurantId);

        assertTrue(result.isEmpty());
        verify(trazabilidadPersistencePort).findByOrderIds(orderIds);
    }

    @Test
    void shouldReturnEfficiencyListSuccessfully() {
        // 1. Arrange
        Long restaurantId = 1L;
        Long orderId = 100L;
        String clientId = "client-123";
        Long employeeId = 10L;
        LocalDateTime now = LocalDateTime.now();

        List<Long> orderIds = List.of(orderId);

        // Log para el inicio: DEBE SER "EN_PREPARACION"
        Trazabilidad logInicio = new Trazabilidad();
        logInicio.setOrderId(orderId);
        logInicio.setClientId(clientId);
        logInicio.setEmployeeId(employeeId);
        logInicio.setNewStatus("EN_PREPARACION"); // <--- Clave 1
        logInicio.setDate(now.minusMinutes(45));

        // Log para el fin: DEBE SER "ENTREGADO"
        Trazabilidad logFin = new Trazabilidad();
        logFin.setOrderId(orderId);
        logFin.setClientId(clientId);
        logFin.setEmployeeId(employeeId);
        logFin.setNewStatus("ENTREGADO"); // <--- Clave 2
        logFin.setDate(now);

        List<Trazabilidad> trazabilidades = List.of(logInicio, logFin);

        when(orderPersistencePort.getOrdersByRestaurantId(restaurantId)).thenReturn(orderIds);
        when(trazabilidadPersistencePort.findByOrderIds(orderIds)).thenReturn(trazabilidades);

        // 2. Act
        List<Eficiencia> result = trazabilidadUseCase.getEfficiency(restaurantId);

        // 3. Assert
        assertAll("Validación de lista de eficiencia",
                () -> assertFalse(result.isEmpty(), "La lista NO debería estar vacía"),
                () -> assertEquals(1, result.size()),
                () -> assertEquals(orderId, result.get(0).getOrderId()),
                () -> assertEquals(clientId, result.get(0).getClientId()),
                () -> assertEquals(employeeId, result.get(0).getEmployeeId()),
                () -> assertEquals(45L, result.get(0).getDurationInMinutes(), "La duración debería ser 45 minutos")
        );

        verify(orderPersistencePort).getOrdersByRestaurantId(restaurantId);
        verify(trazabilidadPersistencePort).findByOrderIds(orderIds);
    }

    @Test
    void shouldReturnEmptyListWhenEndDateIsNull() {
        // Arrange
        Long restaurantId = 1L;
        Long orderId = 100L;
        List<Long> orderIds = List.of(orderId);

        // Solo creamos el log de inicio, falta el de "ENTREGADO"
        Trazabilidad logInicio = new Trazabilidad();
        logInicio.setOrderId(orderId);
        logInicio.setNewStatus("EN_PREPARACION");
        logInicio.setDate(LocalDateTime.now());

        List<Trazabilidad> trazabilidades = List.of(logInicio);

        when(orderPersistencePort.getOrdersByRestaurantId(restaurantId)).thenReturn(orderIds);
        when(trazabilidadPersistencePort.findByOrderIds(orderIds)).thenReturn(trazabilidades);

        // Act
        List<Eficiencia> result = trazabilidadUseCase.getEfficiency(restaurantId);

        // Assert
        // Al retornar null en calculateEfficiency, el filter(Objects::nonNull) lo elimina
        assertTrue(result.isEmpty(), "La lista debería estar vacía porque falta el log de ENTREGADO");
    }

    @Test
    void shouldReturnEmptyListWhenStartDateIsNull() {
        // Arrange
        Long restaurantId = 1L;
        Long orderId = 200L;
        List<Long> orderIds = List.of(orderId);

        // Solo creamos el log de fin, falta el de "EN_PREPARACION"
        Trazabilidad logFin = new Trazabilidad();
        logFin.setOrderId(orderId);
        logFin.setNewStatus("ENTREGADO");
        logFin.setDate(LocalDateTime.now());

        List<Trazabilidad> trazabilidades = List.of(logFin);

        when(orderPersistencePort.getOrdersByRestaurantId(restaurantId)).thenReturn(orderIds);
        when(trazabilidadPersistencePort.findByOrderIds(orderIds)).thenReturn(trazabilidades);

        // Act
        List<Eficiencia> result = trazabilidadUseCase.getEfficiency(restaurantId);

        // Assert
        assertTrue(result.isEmpty(), "La lista debería estar vacía porque falta el log de EN_PREPARACION");
    }

    @Test
    void shouldReturnEmployeesEfficiencyRankingSuccessfully() {
        // --- ARRANGE ---
        Long restaurantId = 1L;
        Long userId = 10L;
        Long employeeId = 50L;
        LocalDateTime now = LocalDateTime.now();

        // 1. El restaurante tiene al empleado 50
        when(restaurantPersistencePort.getEmployeesByRestaurantId(restaurantId, userId))
                .thenReturn(List.of(employeeId));

        // 2. El restaurante tiene DOS órdenes (Importante para que el conteo de 2)
        List<Long> orderIds = List.of(101L, 102L);
        when(orderPersistencePort.getOrdersByRestaurantId(restaurantId)).thenReturn(orderIds);

        // 3. Creamos logs para AMBAS órdenes
        List<Trazabilidad> logs = List.of(
                // Orden 101: Duración 30 min
                crearLog(101L, "EN_PREPARACION", employeeId, now.minusMinutes(30)),
                crearLog(101L, "ENTREGADO", employeeId, now),

                // Orden 102: Duración 60 min
                crearLog(102L, "EN_PREPARACION", employeeId, now.minusMinutes(60)),
                crearLog(102L, "ENTREGADO", employeeId, now)
        );

        when(trazabilidadPersistencePort.findByOrderIds(orderIds)).thenReturn(logs);

        // --- ACT ---
        List<RankingEficienciaEmpleado> result = trazabilidadUseCase.getEmployeesEfficiencyRanking(restaurantId, userId);

        // --- ASSERT ---
        assertAll("Validación de Ranking",
                () -> assertFalse(result.isEmpty(), "La lista no debe estar vacía"),
                () -> assertEquals(1, result.size(), "Debería haber 1 empleado en el ranking"),
                () -> assertEquals(2, result.get(0).getOrdersCount(), "El empleado debe tener 2 órdenes"),
                () -> assertEquals(45L, result.get(0).getAverageDurationInMinutes(), "El promedio de 30 y 60 debe ser 45")
        );
    }

    // --- MÉTODO HELPER (Créalos al final de tu clase de test) ---
    private Trazabilidad crearLog(Long orderId, String status, Long employeeId, LocalDateTime date) {
        Trazabilidad log = new Trazabilidad();
        log.setOrderId(orderId);
        log.setNewStatus(status);
        log.setEmployeeId(employeeId);
        log.setClientId("client-123");
        log.setDate(date);
        return log;
    }

    @Test
    void shouldReturnEmptyRankingWhenNoEmployeesFound() {
        // Arrange
        Long restaurantId = 1L;
        Long userId = 10L;

        // Simulamos que el puerto devuelve una lista vacía de empleados
        when(restaurantPersistencePort.getEmployeesByRestaurantId(restaurantId, userId))
                .thenReturn(Collections.emptyList());

        // Act
        List<RankingEficienciaEmpleado> result = trazabilidadUseCase.getEmployeesEfficiencyRanking(restaurantId, userId);

        // Assert
        assertTrue(result.isEmpty(), "El ranking debería estar vacío si no hay empleados");

        // Verificamos que ni siquiera intenta calcular eficiencias porque ya salió del método
        verify(restaurantPersistencePort).getEmployeesByRestaurantId(restaurantId, userId);
        verifyNoInteractions(orderPersistencePort);
    }

    @Test
    void shouldReturnEmptyRankingWhenNoEfficienciesCalculated() {
        // Arrange
        Long restaurantId = 1L;
        Long userId = 10L;
        List<Long> employeesId = List.of(50L);

        when(restaurantPersistencePort.getEmployeesByRestaurantId(restaurantId, userId))
                .thenReturn(employeesId);

        // Simulamos que no hay órdenes para este restaurante, lo que hará que getEfficiency devuelva lista vacía
        when(orderPersistencePort.getOrdersByRestaurantId(restaurantId))
                .thenReturn(Collections.emptyList());

        // Act
        List<RankingEficienciaEmpleado> result = trazabilidadUseCase.getEmployeesEfficiencyRanking(restaurantId, userId);

        // Assert
        assertTrue(result.isEmpty(), "El ranking debería estar vacío si no hay cálculos de eficiencia");
    }

}
