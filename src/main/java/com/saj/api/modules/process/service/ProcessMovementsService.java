package com.saj.api.modules.process.service;

import com.saj.api.modules.process.controller.dtos.movements.CreateMovementsDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.domain.entities.ProcessMovements;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.modules.process.domain.mappers.ProcessMovementsMapper;
import com.saj.api.modules.process.infrastructure.repository.ProcessMovementsRepository;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.service.UserService;
import com.saj.api.shared.exceptions.BusinessException;
import com.saj.api.shared.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessMovementsService {
    private static final Logger log = LoggerFactory.getLogger(ProcessMovementsService.class);

    private final ProcessMovementsRepository processMovementsRepository;
    private final ProcessMovementsMapper processMovementsMapper;
    private final ProcessService processService;
    private final UserService userService;

    public List<ProcessMovementsResponseDTO> findMovementsByProcessId(UUID id) {
        List<ProcessMovements> movements = processMovementsRepository.findByProcessId(id);

        if (movements.isEmpty()) {
            throw new ObjectNotFoundException("Não foi encontado movimentos para o processo informado");
        }
        return movements.stream()
                .map(processMovementsMapper::toProcessMovementsResponseDTO)
                .toList();
    }


    /*
    * TODO: Verificar a possibiliade de criar uma validação do usuário logado
    */
    public void createProcessMovement(CreateMovementsDTO dto) {
        log.info("Iniciando o cadastro da movimentação de procesos...");
        var process = processService.findById(dto.processId());
        if(process.getStatus() == ProcessStatus.CANCELLED || process.getStatus() == ProcessStatus.FINISHED) {
            log.warn("Tentativa de Adicionar movimentação em processo finalizado/cancelado bloqueada. processId: {}", process.getId());
            throw new BusinessException("Não é possivel adicionar movimentação em processo finalizado ou cancelado");
        }
        var createdBy = UUID.fromString("40976e03-ead4-400e-a27c-c6630cffa64c");
        User user = userService.findById(createdBy);
        ProcessMovements newProcessMovement = processMovementsMapper.toCreateProcessMovement(dto, process, user);
        processMovementsRepository.save(newProcessMovement);
        log.info("Movimentação de processo criada com sucesso!");
    }
}
