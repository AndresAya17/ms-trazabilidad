package com.pragma.powerup.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrazabilidadRequestDto {

    @NotNull(message = "Order id is required")
    private Long orderId;

    @NotBlank(message = "Customer id is required")
    private String clientId;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Customer email must be valid")
    private String clientEmail;

    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @NotBlank(message = "Previous status is required")
    private String previousStatus;

    @NotBlank(message = "New status is required")
    private String newStatus;

    private Long employeeId;

    @Email(message = "Employee email must be valid")
    private String employeeEmail;
}
