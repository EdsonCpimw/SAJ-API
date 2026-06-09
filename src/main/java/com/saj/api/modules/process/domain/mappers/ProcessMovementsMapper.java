package com.saj.api.modules.process.domain.mappers;

import com.saj.api.modules.process.controller.dtos.movements.CreateMovementsDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementResponseDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.controller.dtos.movements.UpdateMovementDTO;
import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.process.domain.entities.ProcessMovements;
import com.saj.api.modules.users.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProcessMovementsMapper {

    @Mapping(target = "processNumber", source = "process.numberProcess")
    ProcessMovementsResponseDTO toProcessMovementsResponseDTO(ProcessMovements processMovements);

    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "process", source = "process")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "important", source = "dto.isImportant")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProcessMovements toCreateProcessMovement(CreateMovementsDTO dto, Process process, User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "process", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "important", source = "dto.isImportant")
    ProcessMovements toUpdateProcessMovement(UpdateMovementDTO dto, User user, @MappingTarget ProcessMovements movement);


    ProcessMovementResponseDTO toProcessMovementResponseDTO(ProcessMovements processMovements);

}
