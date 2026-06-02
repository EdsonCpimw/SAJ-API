package com.saj.api.modules.process.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LegalArea {

    CIVIL("CIVIL"),
    LABOR("TRABALHISTA"),
    CRIMINAL("CRIMINAL"),
    BUSINESS("EMPRESARIAL"),
    TAX("TRIBUTARIO"),
    SOCIAL_SECURITY("PREVIDENCIARIO"),
    FAMILY("FAMILIA"),
    CONSUMER("CONSUMIDOR"),
    ADMINISTRATIVE("ADMINISTRATIVO");

    private final String value;
}
