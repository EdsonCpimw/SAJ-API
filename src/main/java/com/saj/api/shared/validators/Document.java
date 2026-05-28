package com.saj.api.shared.validators;

import jakarta.validation.Payload;

public @interface Document {
    String message() default "CPF ou CNPJ inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
