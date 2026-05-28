package com.saj.api.modules.users.domain.mappers;

import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.modules.users.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UsersResponseDTO toUsersResponseDTO(User user);
}
