package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.powerup.domain.usecase.TrazabilidadUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.TrazabilidadJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ITrazabilidadEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ITrazabilidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITrazabilidadRepository trazabilidadRepository;
    private final ITrazabilidadEntityMapper trazabilidadEntityMapper;

    @Bean
    public ITrazabilidadPersistencePort trazabilidadPersistencePort(){
        return new TrazabilidadJpaAdapter(trazabilidadEntityMapper, trazabilidadRepository);
    }

    @Bean
    public ITrazabilidadServicePort trazabilidadServicePort(ITrazabilidadPersistencePort trazabilidadPersistencePort,
                                                            IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort){
        return new TrazabilidadUseCase(trazabilidadPersistencePort,orderPersistencePort,restaurantPersistencePort);
    }

}