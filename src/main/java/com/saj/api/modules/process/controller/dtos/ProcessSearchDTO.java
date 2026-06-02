package com.saj.api.modules.process.controller.dtos;

import com.saj.api.modules.process.domain.enums.LegalArea;
import com.saj.api.modules.process.domain.enums.ProcessPriority;
import com.saj.api.modules.process.domain.enums.ProcessStatus;

public record ProcessSearchDTO(
        String search,
        ProcessStatus status,
        LegalArea legalArea,
        ProcessPriority priority,
        Integer page,
        Integer size,
        String sortBy,
        String direction
) {
}
