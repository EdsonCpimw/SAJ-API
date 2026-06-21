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

    @Mapping(target = "hasMovements", expression = "java(process.hasMovements())")
    @Mapping(target = "client.id", source = "client.id")
    @Mapping(target = "client.name", source = "client.name")
    @Mapping(target = "client.email", source = "client.email")
    ProcessResponseDTO toProcessResponseDTO(Process process);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "company", source = "company")
    @Mapping(target = "createdBy", source = "user")
    @Mapping(target = "client", source = "client")
    Process toProcessCreate(CreateProcessDTO dto, Company company, User user, User client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    void updateProcessFromDTO(UpdateProcessDTO dto, @MappingTarget Process process, User client);

    Process toUpdateProcessStatus(UpadateStatusProcessDTO dto, @MappingTarget Process process);
}
