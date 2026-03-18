package com.pragma.powerup.infrastructure.out.jpa.rest;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.exception.ErrorCode;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
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
public class RestaurantServiceRestAdapter implements IRestaurantPersistencePort {

    private final RestTemplate restTemplate;

    @Value("${plazoleta.service.url}")
    private String plazoletaServiceUrl;

    @Override
    public List<Long> getEmployeesByRestaurantId(Long restaurantId, Long userId) {

        String url = plazoletaServiceUrl + "/api/v1/plazoleta/restaurant/" + restaurantId + "/employees?userId=" + userId;

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
