package com.saj.api.modules.users.controller.dtos;

import com.saj.api.shared.validators.Document;
import jakarta.validation.constraints.NotBlank;

public record CreateCompanyDTO(
        @NotBlank(message = "Nome da empresa é obrigatório")
        String name,

        @NotBlank(message = "CNPJ é obrigatório")
        @Document
        String document
) {}
