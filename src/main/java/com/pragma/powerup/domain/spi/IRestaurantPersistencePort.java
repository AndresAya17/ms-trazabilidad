package com.pragma.powerup.domain.spi;

import java.util.List;

public interface IRestaurantPersistencePort {
    List<Long> getEmployeesByRestaurantId(Long restaurantId, Long userId);
}
