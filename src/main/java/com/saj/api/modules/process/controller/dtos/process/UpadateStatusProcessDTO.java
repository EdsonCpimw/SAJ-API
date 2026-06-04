package com.saj.api.modules.process.controller.dtos.process;

import com.saj.api.modules.process.domain.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;

public record UpadateStatusProcessDTO(
        @NotNull(message = "Status é obrigatório")
        ProcessStatus status
) {}
