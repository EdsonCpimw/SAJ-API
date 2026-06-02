package com.saj.api.modules.process.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessStatus {

    OPEN("ABERTO"),
    IN_PROGRESS("EM_ANDAMENTO"),
    WAITING("AGUARDANDO"),
    FINISHED("FINALIZADO"),
    CANCELLED("CANCELADO");

    private final String value;
}
