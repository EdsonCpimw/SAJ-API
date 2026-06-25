package com.saj.api.modules.auth.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(
     @NotBlank(message = "Token é obrigatório")
     String token,
     @NotBlank(message = "Senha é obrigatório")
     @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
     String password,
     @NotBlank(message = "Confirmação de senha é obrigatória")
     String confirmPassword
) {}
