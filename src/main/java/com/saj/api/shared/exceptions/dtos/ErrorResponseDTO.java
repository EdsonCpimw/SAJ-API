package com.saj.api.shared.exceptions.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        int status,
        String message,
        LocalDateTime timestamp,
        List<FieldErrorDTO> errors
) {

    public record FieldErrorDTO(
            String field,
            String message
    ){}
}
