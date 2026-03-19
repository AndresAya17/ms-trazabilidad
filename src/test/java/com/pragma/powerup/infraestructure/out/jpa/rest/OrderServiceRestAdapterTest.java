package com.pragma.powerup.infraestructure.out.jpa.rest;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.infrastructure.out.jpa.rest.OrderServiceRestAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class OrderServiceRestAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderServiceRestAdapter orderServiceRestAdapter;

    private final String serviceUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        // Refleja el valor de la anotación @Value
        ReflectionTestUtils.setField(orderServiceRestAdapter, "plazoletaServiceUrl", serviceUrl);
    }

    @Test
    void shouldGetOrdersByRestaurantIdSuccessfully() {
        // Arrange
        Long restaurantId = 1L;
        List<Long> expectedIds = List.of(101L, 102L);

        // FIX: Todos los argumentos deben ser matchers
        when(restTemplate.exchange(
                anyString(),                    // Matcher 1
                eq(HttpMethod.GET),             // Matcher 2
                isNull(),                       // Matcher 3 (Esto reemplaza al null pelado)
                any(ParameterizedTypeReference.class) // Matcher 4
        )).thenReturn(ResponseEntity.ok(expectedIds));

        // Act
        List<Long> result = orderServiceRestAdapter.getOrdersByRestaurantId(restaurantId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(101L, result.get(0));
    }

    @Test
    void shouldThrowDomainExceptionWhenRestCallFails() {
        Long restaurantId = 1L;

        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException("Error"));

        assertThrows(DomainException.class, () ->
                orderServiceRestAdapter.getOrdersByRestaurantId(restaurantId)
        );
    }

}
