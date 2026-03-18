package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.model.Eficiencia;
import com.pragma.powerup.domain.model.RankingEficienciaEmpleado;
import com.pragma.powerup.domain.model.Trazabilidad;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.powerup.domain.validator.TrazabilidadDomainValidator;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrazabilidadUseCase implements ITrazabilidadServicePort {

    private final ITrazabilidadPersistencePort trazabilidadPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public TrazabilidadUseCase(ITrazabilidadPersistencePort trazabilidadPersistencePort, IOrderPersistencePort orderPersistencePort,
                               IRestaurantPersistencePort restaurantPersistencePort) {
        this.trazabilidadPersistencePort = trazabilidadPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveLogs(Trazabilidad trazabilidad) {
        TrazabilidadDomainValidator.validate(trazabilidad);
        trazabilidadPersistencePort.saveLogs(trazabilidad);
    }

    @Override
    public List<Trazabilidad> getLogsByClientId(String clientId) {
        return  trazabilidadPersistencePort.getLogsByClientId(clientId);
    }

    @Transactional
    @Override
    public List<Eficiencia> getEfficiency(Long restaurantId) {
        List<Long> orderIds = orderPersistencePort.getOrdersByRestaurantId(restaurantId);

        if (orderIds == null || orderIds.isEmpty()) {
            return List.of();
        }

        List<Trazabilidad> trazabilidades = trazabilidadPersistencePort.findByOrderIds(orderIds);

        if (trazabilidades == null || trazabilidades.isEmpty()) {
            return List.of();
        }

        return trazabilidades.stream()
                .collect(Collectors.groupingBy(Trazabilidad::getOrderId))
                .entrySet()
                .stream()
                .map(entry -> calculateEfficiency(entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull)
                .toList();

    }


    private Eficiencia calculateEfficiency(Long orderId, List<Trazabilidad> logs) {
        LocalDateTime startDate = logs.stream()
                .filter(log -> "EN_PREPARACION".equals(log.getNewStatus()))
                .map(Trazabilidad::getDate)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        LocalDateTime endDate = logs.stream()
                .filter(log -> "ENTREGADO".equals(log.getNewStatus()))
                .map(Trazabilidad::getDate)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        if (startDate == null || endDate == null) {
            return null;
        }

        String clientId = logs.stream()
                .map(Trazabilidad::getClientId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        Long employeeId = logs.stream()
                .filter(log -> "EN_PREPARACION".equals(log.getNewStatus()))
                .map(Trazabilidad::getEmployeeId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        long durationInMinutes = Duration.between(startDate, endDate).toMinutes();

        return new Eficiencia(orderId,clientId,employeeId ,startDate, endDate, durationInMinutes);
    }

    @Override
    public List<RankingEficienciaEmpleado> getEmployeesEfficiencyRanking(Long restaurantId, Long userId) {

        List<Long> employeesId = restaurantPersistencePort.getEmployeesByRestaurantId(restaurantId,userId);


        if (employeesId == null || employeesId.isEmpty()) {
            return List.of();
        }

        List<Eficiencia> eficiencias = getEfficiency(restaurantId);

        if (eficiencias == null || eficiencias.isEmpty()) {
            return List.of();
        }

        return eficiencias.stream()
                .filter(eficiencia -> eficiencia.getEmployeeId() != null)
                .filter(eficiencia -> employeesId.contains(eficiencia.getEmployeeId()))
                .collect(Collectors.groupingBy(Eficiencia::getEmployeeId))
                .entrySet()
                .stream()
                .map(entry -> buildEmployeeRanking(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(RankingEficienciaEmpleado::getAverageDurationInMinutes))
                .toList();
    }

    private RankingEficienciaEmpleado buildEmployeeRanking(Long employeeId, List<Eficiencia> eficiencias) {
        long ordersCount = eficiencias.size();

        long averageDurationInMinutes = Math.round(
                eficiencias.stream()
                        .mapToLong(Eficiencia::getDurationInMinutes)
                        .average()
                        .orElse(0)
        );

        return new RankingEficienciaEmpleado(employeeId, ordersCount, averageDurationInMinutes);
    }
}
