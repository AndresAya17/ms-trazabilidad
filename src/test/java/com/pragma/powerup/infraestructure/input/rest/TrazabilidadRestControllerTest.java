package com.pragma.powerup.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import com.pragma.powerup.infrastructure.input.rest.TrazabilidadRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(TrazabilidadRestController.class)
class TrazabilidadRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ITrazabilidadHandler trazabilidadHandler;

    @Test
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

}
