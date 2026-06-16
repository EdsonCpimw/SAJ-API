package com.saj.api.modules.process.service;

import com.saj.api.modules.auth.service.AuthService;
import com.saj.api.modules.process.controller.dtos.movements.CreateMovementsDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementResponseDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.controller.dtos.movements.UpdateMovementDTO;
import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.process.domain.entities.ProcessMovements;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.modules.process.domain.events.MovementCreatedEvent;
import com.saj.api.modules.process.domain.mappers.ProcessMovementsMapper;
import com.saj.api.modules.process.infrastructure.repository.ProcessMovementsRepository;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.shared.exceptions.BusinessException;
import com.saj.api.shared.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessMovementsService {
    private static final Logger log = LoggerFactory.getLogger(ProcessMovementsService.class);

    private final ProcessMovementsRepository processMovementsRepository;
    private final ProcessMovementsMapper processMovementsMapper;
    private final ProcessService processService;
    private final AuthService authService;
    private final ApplicationEventPublisher eventPublisher;


    public List<ProcessMovementsResponseDTO> findMovementsByProcessId(UUID id) {
        List<ProcessMovements> movements = processMovementsRepository.findByProcess_Id(id);

        if (movements.isEmpty()) {
            throw new ObjectNotFoundException("Não foi encontado movimentos para o processo informado");
        }
        return movements.stream()
                .map(processMovementsMapper::toProcessMovementsResponseDTO)
                .toList();
    }

    public ProcessMovements findProcessMovementById(UUID id) {
        return processMovementsRepository.findById(id).orElseThrow(() -> {
            log.warn("Movimentação do processo não encontrado. id: {}", id);
            return new ObjectNotFoundException("Movimentação não encontrado");
        });
    }

    public ProcessMovementResponseDTO findMovementById(UUID id) {
        log.info("Buscando a movimentação pelo id: {}", id);
        var movement = findProcessMovementById(id);
        return processMovementsMapper.toProcessMovementResponseDTO(movement);
    }

    @Transactional
    public void createProcessMovement(CreateMovementsDTO dto) {
        log.info("Iniciando o cadastro da movimentação de procesos...");
        User authenticatedUser = authService.getCurrentUser();
        var process = processService.findById(dto.processId());
        if(process.getStatus() == ProcessStatus.CANCELLED || process.getStatus() == ProcessStatus.FINISHED) {
            log.warn("Tentativa de Adicionar movimentação em processo finalizado/cancelado bloqueada. processId: {}", process.getId());
            throw new BusinessException("Não é possivel adicionar movimentação em processo finalizado ou cancelado");
        }
        validateUserBelongsToCompany(authenticatedUser, process);
        ProcessMovements newProcessMovement = processMovementsMapper.toCreateProcessMovement(dto, process, authenticatedUser);
        var savedMovement = processMovementsRepository.save(newProcessMovement);
        var moviment = processMovementsRepository.findByIdWithClient(savedMovement.getId()).orElseThrow(() -> {
            log.warn("Movimentação de processo não encontrado id: {}", savedMovement.getId());
            return new ObjectNotFoundException("Problema para cadastar a movimentação de processo");
        });
        eventPublisher.publishEvent(new MovementCreatedEvent(moviment));
        log.info("Movimentação de processo criada com sucesso!");
    }

    private void validateUserBelongsToCompany(User user, Process process) {
        if (!user.getCompany().getId().equals(process.getCompany().getId())) {
            log.warn(
                    "Não é permitido o usuário com id: {} altere uma movimentação de processo para a empresa de id: {}",
                    user.getId(),
                    process.getCompany().getId()
            );
            throw new BusinessException("Não foi possível criar a movimentação de prceosso");
        }
    }

    public void updateProcessMovement(UUID id, UpdateMovementDTO dto) {
        log.info("Iniciando a atualização da movimentação do processo...");
        ProcessMovements oldMovement = findProcessMovementById(id);
        User authenticatedUser = authService.getCurrentUser();

        var processMovement = processMovementsMapper.toUpdateProcessMovement(dto, authenticatedUser, oldMovement);
        processMovementsRepository.save(processMovement);
        log.info("Movimentação de processo atualizado com sucesso. id: {}", id);

    }
}
