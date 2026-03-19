package com.pragma.powerup.infraestructure.out.jpa.rest;

import com.pragma.powerup.infrastructure.out.jpa.rest.RestaurantServiceRestAdapter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import com.pragma.powerup.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceRestAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestaurantServiceRestAdapter restaurantServiceRestAdapter;

    private final String serviceUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(restaurantServiceRestAdapter, "plazoletaServiceUrl", serviceUrl);
    }

    @Test
    void shouldGetEmployeesByRestaurantIdSuccessfully() {
        // Arrange
        Long restaurantId = 1L;
        Long userId = 10L;
        List<Long> expectedEmployees = List.of(50L, 51L);

        // Usamos isNull() para el cuerpo del request (3er parámetro)
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(expectedEmployees));

        // Act
        List<Long> result = restaurantServiceRestAdapter.getEmployeesByRestaurantId(restaurantId, userId);

        // Assert
        assertAll("Validación de respuesta del Adapter",
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(50L, result.get(0))
        );
    }

    @Test
    void shouldThrowDomainExceptionWhenRestaurantCallFails() {
        // Arrange
        Long restaurantId = 1L;
        Long userId = 10L;

        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException("Connection error"));

        // Act & Assert (Cubre la línea roja del catch)
        assertThrows(DomainException.class, () ->
                restaurantServiceRestAdapter.getEmployeesByRestaurantId(restaurantId, userId)
        );
    }

}
