package com.saj.api.modules.users.controller.dtos;

public record UserSearchDTO(
        String search,
        Boolean active,
        Integer page,
        Integer size,
        String sortBy,
        String direction
) {}
