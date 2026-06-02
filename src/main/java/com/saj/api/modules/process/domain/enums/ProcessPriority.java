package com.saj.api.modules.process.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessPriority {

    LOW("BAIXA"),
    MEDIUM("MEDIA"),
    HIGH("ALTA"),
    URGENT("URGENTE");

    private final String value;
}
