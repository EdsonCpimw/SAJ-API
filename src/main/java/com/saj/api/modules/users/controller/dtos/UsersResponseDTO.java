package com.saj.api.modules.users.controller.dtos;

import com.saj.api.modules.users.domain.entities.Company;

import java.util.UUID;

public record UsersResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        boolean active,
        String companyName
) {}
