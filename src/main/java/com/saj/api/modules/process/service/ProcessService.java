package com.saj.api.modules.process.service;

import com.saj.api.modules.process.controller.dtos.ProcessResponseDTO;
import com.saj.api.modules.process.controller.dtos.ProcessSearchDTO;
import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.process.domain.mappers.ProcessMapper;
import com.saj.api.modules.process.infrastructure.repository.ProcessRepository;
import com.saj.api.modules.process.infrastructure.specifications.ProcessSpecification;
import com.saj.api.shared.dto.PaginationResponseDTO;
import com.saj.api.shared.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {
    private static final Logger log = LoggerFactory.getLogger(ProcessService.class);

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("numberProcess", "title", "courtDivision");

    private final ProcessRepository processRepository;
    private final ProcessMapper processMapper;

    public PaginationResponseDTO<ProcessResponseDTO> findAllProcessSearch(ProcessSearchDTO filter) {
        log.info("Iniciando consulta de processos...");
        Specification<Process> spec = Specification
                .where(ProcessSpecification.search(filter.search()))
                .and(ProcessSpecification.hasStatus(filter.status()))
                .and(ProcessSpecification.hasLegalArea(filter.legalArea()))
                .and(ProcessSpecification.hasProcessPriority(filter.priority()));

        int page = filter.page() != null && filter.page() >= 0 ? filter.page() : 0;
        int size = filter.size() != null && filter.size() > 0 ? filter.size() : 10;

        String sortBy = filter.sortBy() != null && ALLOWED_SORT_FIELDS.contains(filter.sortBy())
                ? filter.sortBy()
                : "numberProcess";
        String direction = filter.direction() != null ? filter.direction() : "asc";

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Page<ProcessResponseDTO> result = processRepository
                .findAll(spec, pageable)
                .map(processMapper::toProcessResponseDTO);

        return PageUtils.from(result);

    }
}
