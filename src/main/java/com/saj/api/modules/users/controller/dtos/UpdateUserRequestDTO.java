package com.saj.api.modules.users.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUserRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank(message = "Sobrenome é obrigatório")
        String lastName,
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}",
                message = "Telefone  inválido. Formato esperado: (XX) XXXXX-XXXX"
        )
        String phone
) {}
