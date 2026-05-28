package com.saj.api.modules.auth.controller.dtos;

import com.saj.api.modules.users.controller.dtos.CreateCompanyDTO;
import com.saj.api.modules.users.controller.dtos.CreateUserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @Valid
        @NotNull(message = "Dados da empresa são obrigatórios")
        CreateCompanyDTO company,
        @Valid
        @NotNull(message = "Dados do usuário são obrigatório")
        CreateUserDTO user
) {}
