package com.saj.api.modules.process.domain.mappers;

import com.saj.api.modules.process.controller.dtos.process.CreateProcessDTO;
import com.saj.api.modules.process.controller.dtos.process.ProcessResponseDTO;
import com.saj.api.modules.process.controller.dtos.process.UpadateStatusProcessDTO;
import com.saj.api.modules.process.controller.dtos.process.UpdateProcessDTO;
import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProcessMapper {

    ProcessResponseDTO toProcessResponseDTO(Process process);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "company", source = "company")
    @Mapping(target = "createdBy", source = "user")
    Process toProcessCreate(CreateProcessDTO dto, Company company, User user);

    void updateProcessFromDTO(UpdateProcessDTO dto, @MappingTarget Process process);

    Process updateProcessStatus(UpadateStatusProcessDTO dto, @MappingTarget Process process);
}
