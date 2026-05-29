package com.saj.api.modules.users.domain.mappers;

import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.modules.users.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "companyName", source = "company.name")
    UsersResponseDTO toUsersResponseDTO(User user);
}
