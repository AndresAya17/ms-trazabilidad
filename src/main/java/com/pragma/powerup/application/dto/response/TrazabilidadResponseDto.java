package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrazabilidadResponseDto {

    private Long orderId;

    private String clientId;

    private String clientEmail;

    private LocalDateTime date;

    private String previousStatus;

    private String newStatus;

    private Long employeeId;

    private String employeeEmail;

}
