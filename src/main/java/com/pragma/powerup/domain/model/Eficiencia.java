package com.pragma.powerup.domain.model;

import java.time.LocalDateTime;


public class Eficiencia {
    private Long orderId;
    private String clientId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Long durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    private Long employeeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long durationInMinutes;

    public Eficiencia(Long orderId, String clientId, Long employeeId, LocalDateTime startDate, LocalDateTime endDate, Long durationInMinutes) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationInMinutes = durationInMinutes;
    }
}
