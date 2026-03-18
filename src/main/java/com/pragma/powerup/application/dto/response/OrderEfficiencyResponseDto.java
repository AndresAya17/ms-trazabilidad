package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderEfficiencyResponseDto {
    private Long orderId;
    private String clientId;
    private Long employeeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long durationInMinutes;
}
