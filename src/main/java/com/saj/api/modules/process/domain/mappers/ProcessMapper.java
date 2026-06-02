package com.saj.api.modules.process.domain.mappers;

import com.saj.api.modules.process.controller.dtos.ProcessResponseDTO;
import com.saj.api.modules.process.domain.entities.Process;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessMapper {

    ProcessResponseDTO toProcessResponseDTO(Process process);
}
