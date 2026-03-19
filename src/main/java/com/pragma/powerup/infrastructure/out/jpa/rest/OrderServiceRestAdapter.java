package com.pragma.powerup.infrastructure.out.jpa.rest;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.exception.ErrorCode;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceRestAdapter implements IOrderPersistencePort {

    private final RestTemplate restTemplate;

    @Value("${plazoleta.service.url}")
    private String plazoletaServiceUrl;

    @Override
    public List<Long> getOrdersByRestaurantId(Long restaurantId) {

        String url = plazoletaServiceUrl + "/api/v1/plazoleta/order/restaurant/" + restaurantId + "/orders";

        try {
            ResponseEntity<List<Long>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Long>>() {
                    }
            );

            return response.getBody() != null ? response.getBody() : Collections.emptyList();

        } catch (RestClientException e) {
            throw new DomainException(
                    ErrorCode.EXTERNAL_SERVICE_ERROR,
                    "Error communicating with plazoleta service"
            );
        }
    }
}
