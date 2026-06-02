package com.saj.api.shared.dto;

import java.util.List;

public record PaginationResponseDTO<T>(
        List<T> data,
        long totalElements,
        int totalPages,
        int page,
        int size,
        boolean first,
        boolean last
) {}
