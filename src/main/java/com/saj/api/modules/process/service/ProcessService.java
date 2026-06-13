package com.saj.api.modules.process.service;

import com.saj.api.modules.auth.controller.dtos.AuthenticatedUser;
import com.saj.api.modules.auth.service.AuthService;
import com.saj.api.modules.process.controller.dtos.process.*;
import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.process.domain.mappers.ProcessMapper;
import com.saj.api.modules.process.infrastructure.repository.ProcessRepository;
import com.saj.api.modules.process.infrastructure.specifications.ProcessSpecification;
import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.service.CompanyService;
import com.saj.api.modules.users.service.UserService;
import com.saj.api.shared.dto.PaginationResponseDTO;
import com.saj.api.shared.exceptions.ObjectNotFoundException;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessService {
    private static final Logger log = LoggerFactory.getLogger(ProcessService.class);

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("numberProcess", "title", "courtDivision");

    private final ProcessRepository processRepository;
    private final ProcessMapper processMapper;
    private final CompanyService companyService;
    private final UserService userService;
    private final AuthService authService;


    public PaginationResponseDTO<ProcessResponseDTO> findAllProcessSearch(ProcessSearchDTO filter) {
        log.info("Iniciando consulta de processos...");

        User authenticatedUser = authService.getCurrentUser();

        System.out.println("Usuário logado: id: " + authenticatedUser.getId()
                + ", email: " + authenticatedUser.getEmail()
                + ", company: " + authenticatedUser.getCompany().getId());

        Specification<Process> spec = Specification
                .where(ProcessSpecification.companyContains(authenticatedUser.getCompany()))
                .and(ProcessSpecification.search(filter.search()))
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

    public Process findById(UUID id) {
        return processRepository.findById(id).orElseThrow(() -> {
            log.warn("Processo não encontrado. id: {}", id);
            return new ObjectNotFoundException("Processo não encontrado");
        });
    }

    public void createProcess(CreateProcessDTO dto) {
        log.info("Iniciando cadastro de um processo...");
        User authenticatedUser = authService.getCurrentUser();
        Company company = companyService.companyFindById(authenticatedUser.getCompany().getId());
        User user = userService.findById(authenticatedUser.getId());

        Process newProcess = processMapper.toProcessCreate(dto, company, user);
        processRepository.save(newProcess);
        log.info("Processo criado com sucesso. numero do processo: {}", dto.numberProcess());
    }


    public void updateProcess(UUID id, UpdateProcessDTO dto) {
        log.info("Iniciando atualização de processo... id: {}", id);
        var oldProcess = findById(id);
        processMapper.updateProcessFromDTO(dto, oldProcess);
        processRepository.save(oldProcess);
        log.info("Processo atualizado com sucesso. id: {}", id);
    }

    public ProcessResponseDTO findProcessById(UUID id) {
        log.info("Buscando processo pelo id: {}", id);
        return processMapper.toProcessResponseDTO(findById(id));
    }

    public void updateProcessStatusById(UUID id, UpadateStatusProcessDTO dto) {
        log.info("Iniciando a atualização do status do processo... id: {}", id);
        var process = findById(id);
        var newStatusProcess = processMapper.toUpdateProcessStatus(dto, process);
        processRepository.save(newStatusProcess);
        log.info("Status do processo atualizado com sucesso. id: {}", id);
    }
}
