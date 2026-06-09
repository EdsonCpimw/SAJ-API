package com.saj.api.modules.process.controller.dtos.movements;

import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.modules.process.domain.enums.TypeMovements;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProcessMovementResponseDTO(
        UUID id,
        String title,
        String description,
        TypeMovements type,
        LocalDateTime dateEvent,
        ProcessStatus status,
        boolean isImportant
) {}
