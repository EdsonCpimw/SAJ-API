package com.saj.api.shared.utils;

import com.saj.api.shared.dto.PageResponseDTO;
import org.springframework.data.domain.Page;

public final class PageUtils {
    private PageUtils(){}

    public static <T> PageResponseDTO<T> from(Page<T> page) {
        return new PageResponseDTO<>(
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
