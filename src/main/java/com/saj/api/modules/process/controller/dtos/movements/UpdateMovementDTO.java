package com.saj.api.modules.process.controller.dtos.movements;

import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.modules.process.domain.enums.TypeMovements;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateMovementDTO(
        @NotNull(message = "Id do processo é obrigário")
        UUID processId,
        @NotBlank(message = "Título é obrigatório")
        String title,
        @NotBlank(message = "Descrição é obrigatório")
        String description,
        @NotNull(message = "Tipo de movimentação é obrigatório")
        TypeMovements type,
        LocalDateTime dateEvent,
        @NotNull(message = "Status é obrigatório")
        ProcessStatus status,
        boolean isImportant
) {}
