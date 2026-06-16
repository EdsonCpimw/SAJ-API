package com.saj.api.modules.process.controller.dtos.process;

import com.saj.api.modules.process.domain.enums.LegalArea;
import com.saj.api.modules.process.domain.enums.ProcessPriority;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateProcessDTO(
        @NotBlank(message = "Numero do processo é obrigatório")
        String numberProcess,
        @NotBlank(message = "Título é obrigatório")
        String title,
        @NotBlank(message = "Descrição é obrigatório")
        String description,
        @NotNull(message = "Status é obrigatório")
        ProcessStatus status,
        @NotNull(message = "Prioridade é obrigatório")
        ProcessPriority priority,
        @NotNull(message = "Área jurídica é obrigatório")
        LegalArea legalArea,
        @NotBlank(message = "Vara é obrigatório")
        String courtDivision,
        @NotBlank(message = "Tribunal é obrigatório")
        String court,
        @NotNull(message = "Cliente é obrigatório")
        UUID clientId

) {}
