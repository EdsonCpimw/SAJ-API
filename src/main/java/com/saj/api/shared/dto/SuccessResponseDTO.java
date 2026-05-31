package com.saj.api.shared.dto;

import java.time.LocalDateTime;

public record SuccessResponseDTO(
        int status,
        String message,
        LocalDateTime timestamp
) {
    public static SuccessResponseDTO of(String message) {
        return new SuccessResponseDTO(200, message, LocalDateTime.now());
    }
}
