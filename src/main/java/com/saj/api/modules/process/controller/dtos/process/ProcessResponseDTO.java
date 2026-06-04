package com.saj.api.modules.process.controller.dtos.process;

import com.saj.api.modules.process.domain.enums.LegalArea;
import com.saj.api.modules.process.domain.enums.ProcessPriority;
import com.saj.api.modules.process.domain.enums.ProcessStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProcessResponseDTO(
        UUID id,
        String numberProcess,
        String title,
        String description,
        ProcessStatus status,
        LegalArea legalArea,
        String courtDivision,
        String court,
        ProcessPriority priority,
        LocalDateTime createdAt
) {}
