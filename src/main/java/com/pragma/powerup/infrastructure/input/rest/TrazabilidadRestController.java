package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.RankingEficienciaEmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import com.pragma.powerup.infrastructure.util.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class TrazabilidadRestController {

    private final ITrazabilidadHandler trazabilidadHandler;


    @PostMapping
    public ResponseEntity<Void> saveLog(@RequestBody TrazabilidadRequestDto trazabilidadRequestDto) {
        trazabilidadHandler.saveLogs(trazabilidadRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize(SecurityConstants.HAS_CLIENT)
    @GetMapping("/{clientId}")
    public ResponseEntity<List<TrazabilidadResponseDto>> findByClientId(
            @PathVariable String clientId){
        return ResponseEntity.ok(trazabilidadHandler.getLogsByClientId(clientId));
    }

    @PreAuthorize(SecurityConstants.HAS_OWNER)
    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<List<OrderEfficiencyResponseDto>> getefficiencyOrder(
            @PathVariable Long restaurantId){
        return ResponseEntity.ok(trazabilidadHandler.getEfficiency(restaurantId));
    }

    @PreAuthorize(SecurityConstants.HAS_OWNER)
    @GetMapping("/orders/ranking")
    public ResponseEntity<List<RankingEficienciaEmpleadoResponseDto>> getEmployeesEfficiencyRanking(
            @RequestParam Long restaurantId,
            @RequestAttribute("auth.userId") Long userId) {
        return ResponseEntity.ok(trazabilidadHandler.getEmployeesEfficiencyRanking(restaurantId,userId));
    }

}
