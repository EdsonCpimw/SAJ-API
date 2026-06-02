package com.saj.api.modules.process.domain.mappers;

import com.saj.api.modules.process.controller.dtos.CreateProcessDTO;
import com.saj.api.modules.process.controller.dtos.ProcessResponseDTO;
import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProcessMapper {

    ProcessResponseDTO toProcessResponseDTO(Process process);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "company", source = "company")
    @Mapping(target = "createdBy", source = "user")
    Process toProcessCreate(CreateProcessDTO dto, Company company, User user);
}
