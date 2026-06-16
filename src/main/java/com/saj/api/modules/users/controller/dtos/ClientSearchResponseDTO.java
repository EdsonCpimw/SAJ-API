package com.saj.api.modules.users.controller.dtos;

import java.util.UUID;

public record ClientSearchResponseDTO(
        UUID id,
        String name,
        String email
) {
}
