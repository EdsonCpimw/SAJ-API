package com.saj.api.shared.utils;

import com.saj.api.shared.dto.PaginationResponseDTO;
import org.springframework.data.domain.Page;

public final class PageUtils {
    private PageUtils(){}

    public static <T> PaginationResponseDTO<T> from(Page<T> page) {
        return new PaginationResponseDTO<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );
    }
}
