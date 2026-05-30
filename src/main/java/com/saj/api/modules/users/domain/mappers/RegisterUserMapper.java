package com.saj.api.modules.users.domain.mappers;

import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
import com.saj.api.modules.users.controller.dtos.CreateUserDTO;
import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.shared.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {StringUtils.class})
public interface RegisterUserMapper {

    @Mapping(target = "name", source = "dto.company.name")
    @Mapping(target = "document", expression = "java(StringUtils.removeFormatting(dto.company().document()))")
    @Mapping(target = "id", ignore = true)
    Company toCompany(RegisterRequestDTO dto);

    @Mapping(target = "name", source = "dto.user.name")
    @Mapping(target = "email", source = "dto.user.email")
    @Mapping(target = "phone", source = "dto.user.phone")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "id", ignore = true)
    User toUserRegister(RegisterRequestDTO dto, Company company, String hashedPassword);


    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "phone", source = "dto.phone")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "id", ignore = true)
    User toUserCreate(CreateUserDTO dto, Company company, String hashedPassword);
}
