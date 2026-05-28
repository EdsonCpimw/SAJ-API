package com.saj.api.modules.users.controller.dtos;

import java.util.UUID;

public record UsersResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        boolean active
) {}
