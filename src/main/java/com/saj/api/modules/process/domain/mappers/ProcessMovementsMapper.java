package com.saj.api.modules.process.domain.mappers;

import com.saj.api.modules.process.controller.dtos.movements.CreateMovementsDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.process.domain.entities.ProcessMovements;
import com.saj.api.modules.users.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProcessMovementsMapper {

    ProcessMovementsResponseDTO toProcessMovementsResponseDTO(ProcessMovements processMovements);

    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "process", source = "process")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "isImportant", source = "dto.isImportant")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProcessMovements toCreateProcessMovement(CreateMovementsDTO dto, Process process, User user);
}
