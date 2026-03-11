package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{clientId}")
    public ResponseEntity<List<TrazabilidadResponseDto>> findByClientId(
            @PathVariable String clientId){
        return ResponseEntity.ok(trazabilidadHandler.getLogsByClientId(clientId));
    }

}
