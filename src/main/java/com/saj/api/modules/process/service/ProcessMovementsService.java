package com.saj.api.modules.process.service;

import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.domain.entities.ProcessMovements;
import com.saj.api.modules.process.domain.mappers.ProcessMovementsMapper;
import com.saj.api.modules.process.infrastructure.repository.ProcessMovementsRepository;
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

    public List<ProcessMovementsResponseDTO> findMovementsByProcessId(UUID id) {
        List<ProcessMovements> movements = processMovementsRepository.findByProcessId(id);

        if (movements.isEmpty()) {
            throw new ObjectNotFoundException("Não foi encontado movimentos para o processo informado");
        }
        return movements.stream()
                .map(processMovementsMapper::toProcessMovementsResponseDTO)
                .toList();
    }
}
