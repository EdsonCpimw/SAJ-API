package com.saj.api.modules.auth.service;

import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.domain.mappers.RegisterUserMapper;
import com.saj.api.modules.users.service.CompanyService;
import com.saj.api.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RegisterUserMapper registerUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CompanyService companyService;

    @Transactional
    public void register(RegisterRequestDTO dto) {
        userService.validateEmailAlreadyExists(dto.user().email());

        Company company = registerUserMapper.toCompany(dto);
        var newCompany = companyService.createCompany(company);
        User user = registerUserMapper.toUserRegister(dto, newCompany, passwordEncoder.encode(dto.user().password()));

        this.userService.saveUser(user);

    }


}
