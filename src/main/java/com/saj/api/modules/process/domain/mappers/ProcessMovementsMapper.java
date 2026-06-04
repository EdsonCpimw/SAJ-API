package com.saj.api.modules.process.domain.mappers;

import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.domain.entities.ProcessMovements;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessMovementsMapper {

    ProcessMovementsResponseDTO toProcessMovementsResponseDTO(ProcessMovements processMovements);
}
