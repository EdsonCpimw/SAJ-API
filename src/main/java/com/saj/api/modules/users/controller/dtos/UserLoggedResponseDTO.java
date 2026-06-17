package com.saj.api.modules.users.controller.dtos;

import java.util.UUID;

public record UserLoggedResponseDTO(
        UUID id,
        String name,
        String lastName,
        String phone
) {}
