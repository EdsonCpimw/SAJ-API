package com.saj.api.modules.process.controller.dtos.movements;

import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.process.domain.enums.ProcessStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProcessMovementsResponseDTO(
        UUID id,
        String title,
        String description,
        ProcessStatus status,
        LocalDateTime createdAt
) {}
