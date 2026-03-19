package com.pragma.powerup.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.RankingEficienciaEmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import com.pragma.powerup.domain.spi.IJwtPersistencePort;
import com.pragma.powerup.infrastructure.input.rest.TrazabilidadRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(TrazabilidadRestController.class)
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class
})
@AutoConfigureMockMvc(addFilters = false)
class TrazabilidadRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ITrazabilidadHandler trazabilidadHandler;

    @MockBean
    private IJwtPersistencePort jwtPersistencePort;

    @Test
    @WithMockUser
    void shouldSaveLogSuccessfully() throws Exception {
        TrazabilidadRequestDto requestDto = new TrazabilidadRequestDto();
        requestDto.setOrderId(1L);
        requestDto.setClientId("client-123");
        requestDto.setClientEmail("cliente@test.com");
        requestDto.setDate(LocalDateTime.of(2026, 3, 11, 10, 0));
        requestDto.setPreviousStatus("PENDIENTE");
        requestDto.setNewStatus("EN_PREPARACION");
        requestDto.setEmployeeId(10L);
        requestDto.setEmployeeEmail("empleado@test.com");

        doNothing().when(trazabilidadHandler).saveLogs(any(TrazabilidadRequestDto.class));

        mockMvc.perform(post("/api/v1/logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());

        verify(trazabilidadHandler).saveLogs(any(TrazabilidadRequestDto.class));
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    void shouldFindLogsByClientIdSuccessfully() throws Exception {
        String clientId = "client-123";

        TrazabilidadResponseDto responseDto = new TrazabilidadResponseDto();
        responseDto.setOrderId(1L);
        responseDto.setClientId(clientId);
        responseDto.setClientEmail("cliente@test.com");
        responseDto.setDate(LocalDateTime.of(2026, 3, 11, 10, 0));
        responseDto.setPreviousStatus("PENDIENTE");
        responseDto.setNewStatus("EN_PREPARACION");
        responseDto.setEmployeeId(10L);
        responseDto.setEmployeeEmail("empleado@test.com");

        when(trazabilidadHandler.getLogsByClientId(clientId))
                .thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/v1/logs/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(trazabilidadHandler).getLogsByClientId(clientId);
    }

    @Test
    @WithMockUser(authorities = "OWNER")
    void shouldGetEfficiencyOrderSuccessfully() throws Exception {
        Long restaurantId = 1L;

        OrderEfficiencyResponseDto responseDto = new OrderEfficiencyResponseDto();
        responseDto.setOrderId(100L);
        responseDto.setDurationInMinutes(30L);
        // Configura otros campos según tu DTO

        when(trazabilidadHandler.getEfficiency(restaurantId))
                .thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/v1/logs/{restaurantId}/orders", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$[0].orderId").value(100))
                .andExpect(jsonPath("$[0].durationInMinutes").value(30));

        verify(trazabilidadHandler).getEfficiency(restaurantId);
    }

    @Test
    @WithMockUser(authorities = "OWNER")
    void shouldGetEmployeesEfficiencyRankingSuccessfully() throws Exception {
        Long restaurantId = 1L;
        Long userIdFromToken = 10L;

        RankingEficienciaEmpleadoResponseDto rankingDto = new RankingEficienciaEmpleadoResponseDto();
        rankingDto.setEmployeeId(50L);
        rankingDto.setOrdersCount(5L);
        rankingDto.setAverageDurationInMinutes(25L);

        // Mockeamos el handler usando los dos parámetros
        when(trazabilidadHandler.getEmployeesEfficiencyRanking(restaurantId, userIdFromToken))
                .thenReturn(List.of(rankingDto));

        mockMvc.perform(get("/api/v1/logs/orders/ranking")
                        .param("restaurantId", restaurantId.toString()) // @RequestParam
                        .requestAttr("auth.userId", userIdFromToken)    // @RequestAttribute
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(50))
                .andExpect(jsonPath("$[0].ordersCount").value(5))
                .andExpect(jsonPath("$[0].averageDurationInMinutes").value(25));

        verify(trazabilidadHandler).getEmployeesEfficiencyRanking(restaurantId, userIdFromToken);
    }

}
