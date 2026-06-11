package com.saj.api.modules.auth.controller.dtos;

public record AuthenticatedUser (
    String keycloakId,
    String name,
    String email,
    String firstName,
    String lastName
) {}
