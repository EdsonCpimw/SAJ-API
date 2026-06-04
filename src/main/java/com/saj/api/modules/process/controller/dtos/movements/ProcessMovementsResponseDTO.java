package com.saj.api.modules.process.controller.dtos.movements;

import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.modules.process.domain.enums.TypeMovements;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProcessMovementsResponseDTO(
        UUID id,
        String title,
        String description,
        TypeMovements type,
        ProcessStatus status,
        LocalDateTime createdAt
) {}
