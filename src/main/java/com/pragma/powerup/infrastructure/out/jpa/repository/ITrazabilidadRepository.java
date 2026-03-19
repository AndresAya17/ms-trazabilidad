package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.TrazabilidadEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrazabilidadRepository extends MongoRepository<TrazabilidadEntity, String> {
    List<TrazabilidadEntity> findAllByClientIdOrderByDateDesc(String clientId);
    List<TrazabilidadEntity> findByOrderIdInOrderByDateAsc(List<Long> orderIds);
}
