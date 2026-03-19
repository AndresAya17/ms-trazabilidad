package com.pragma.powerup.infraestructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Trazabilidad;
import com.pragma.powerup.infrastructure.out.jpa.adapter.TrazabilidadJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.TrazabilidadEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ITrazabilidadEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ITrazabilidadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrazabilidadJpaAdapterTest {

    @Mock
    private ITrazabilidadEntityMapper trazabilidadEntityMapper;

    @Mock
    private ITrazabilidadRepository trazabilidadRepository;

    @InjectMocks
    private TrazabilidadJpaAdapter trazabilidadJpaAdapter;

    @Test
    void shouldSaveLogsSuccessfully() {
        Trazabilidad trazabilidad = new Trazabilidad();
        trazabilidad.setOrderId(1L);
        trazabilidad.setClientId("client-123");
        trazabilidad.setClientEmail("cliente@test.com");
        trazabilidad.setDate(LocalDateTime.now());
        trazabilidad.setPreviousStatus("PENDIENTE");
        trazabilidad.setNewStatus("EN_PREPARACION");
        trazabilidad.setEmployeeId(10L);
        trazabilidad.setEmployeeEmail("empleado@test.com");

        TrazabilidadEntity entity = new TrazabilidadEntity();

        when(trazabilidadEntityMapper.toEntity(trazabilidad)).thenReturn(entity);

        trazabilidadJpaAdapter.saveLogs(trazabilidad);

        verify(trazabilidadEntityMapper).toEntity(trazabilidad);
        verify(trazabilidadRepository).save(entity);
    }

    @Test
    void shouldGetLogsByClientIdSuccessfully() {
        String clientId = "client-123";

        TrazabilidadEntity entity1 = new TrazabilidadEntity();
        TrazabilidadEntity entity2 = new TrazabilidadEntity();

        Trazabilidad trazabilidad1 = new Trazabilidad();
        trazabilidad1.setOrderId(1L);
        trazabilidad1.setClientId(clientId);

        Trazabilidad trazabilidad2 = new Trazabilidad();
        trazabilidad2.setOrderId(2L);
        trazabilidad2.setClientId(clientId);

        when(trazabilidadRepository.findAllByClientIdOrderByDateDesc(clientId))
                .thenReturn(List.of(entity1, entity2));

        when(trazabilidadEntityMapper.toModel(entity1)).thenReturn(trazabilidad1);
        when(trazabilidadEntityMapper.toModel(entity2)).thenReturn(trazabilidad2);

        List<Trazabilidad> result = trazabilidadJpaAdapter.getLogsByClientId(clientId);

        verify(trazabilidadRepository).findAllByClientIdOrderByDateDesc(clientId);
        verify(trazabilidadEntityMapper).toModel(entity1);
        verify(trazabilidadEntityMapper).toModel(entity2);

        assertEquals(2, result.size());
        assertSame(trazabilidad1, result.get(0));
        assertSame(trazabilidad2, result.get(1));
    }

    @Test
    void shouldFindByOrderIdsSuccessfully() {
        // Arrange
        List<Long> orderIds = List.of(1L, 2L);
        TrazabilidadEntity entity = new TrazabilidadEntity(); // Asume que existe tu clase Entity
        Trazabilidad model = new Trazabilidad();             // Asume que existe tu clase de Dominio

        // Mock del repositorio devolviendo una lista con una entidad
        when(trazabilidadRepository.findByOrderIdInOrderByDateAsc(orderIds))
                .thenReturn(List.of(entity));

        // Mock del mapper transformando la entidad a modelo
        when(trazabilidadEntityMapper.toModel(entity)).thenReturn(model);

        // Act
        List<Trazabilidad> result = trazabilidadJpaAdapter.findByOrderIds(orderIds);

        // Assert
        assertAll("Verificación de persistencia y mapeo",
                () -> assertFalse(result.isEmpty(), "La lista no debería estar vacía"),
                () -> assertEquals(1, result.size(), "Debería haber un elemento mapeado"),
                () -> verify(trazabilidadRepository).findByOrderIdInOrderByDateAsc(orderIds),
                () -> verify(trazabilidadEntityMapper).toModel(any())
        );
    }

}
