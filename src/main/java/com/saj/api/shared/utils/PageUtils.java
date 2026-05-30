package com.saj.api.shared.utils;

import com.saj.api.shared.dto.PagenationResponseDTO;
import org.springframework.data.domain.Page;

public final class PageUtils {
    private PageUtils(){}

    public static <T> PagenationResponseDTO<T> from(Page<T> page) {
        return new PagenationResponseDTO<>(
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
