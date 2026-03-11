package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.exception.ErrorCode;
import com.pragma.powerup.domain.model.Trazabilidad;

import java.util.regex.Pattern;

public class TrazabilidadDomainValidator {

    private TrazabilidadDomainValidator() {
    }

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static void validate(Trazabilidad trazabilidad) {
        if (trazabilidad == null) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Traceability data must not be null"
            );
        }

        validateOrderId(trazabilidad.getOrderId());
        validateClientId(trazabilidad.getClientId());
        validateClientEmail(trazabilidad.getClientEmail());
        validateDate(trazabilidad.getDate());
        validatePreviousStatus(trazabilidad.getPreviousStatus());
        validateNewStatus(trazabilidad.getNewStatus());
        validateEmployeeId(trazabilidad.getEmployeeId());
        validateEmployeeEmail(trazabilidad.getEmployeeEmail());
    }

    private static void validateOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Order id is required and must be greater than zero"
            );
        }
    }

    private static void validateClientId(String clientId) {
        if (isBlank(clientId)) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Client id is required"
            );
        }
    }

    private static void validateClientEmail(String clientEmail) {
        if (isBlank(clientEmail)) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Client email is required"
            );
        }

        if (!isValidEmail(clientEmail)) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Client email must be valid"
            );
        }
    }

    private static void validateDate(java.time.LocalDateTime date) {
        if (date == null) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Date is required"
            );
        }
    }

    private static void validatePreviousStatus(String previousStatus) {
        if (isBlank(previousStatus)) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Previous status is required"
            );
        }
    }

    private static void validateNewStatus(String newStatus) {
        if (isBlank(newStatus)) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "New status is required"
            );
        }
    }

    private static void validateEmployeeId(Long employeeId) {
        if (employeeId != null && employeeId <= 0) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Employee id must be greater than zero"
            );
        }
    }

    private static void validateEmployeeEmail(String employeeEmail) {
        if (!isBlank(employeeEmail) && !isValidEmail(employeeEmail)) {
            throw new DomainException(
                    ErrorCode.INVALID_DATA,
                    "Employee email must be valid"
            );
        }
    }

    private static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
