package com.pragma.powerup.domain.spi;

import java.util.List;

public interface IOrderPersistencePort {
    List<Long> getOrdersByRestaurantId(Long restaurantId);
}
