package com.saj.api.modules.auth.service;

import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.domain.mappers.UserMapper;
import com.saj.api.modules.users.service.CompanyService;
import com.saj.api.modules.users.service.UserService;
import com.saj.api.shared.exceptions.BusinessException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);

    private final UserMapper registerUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CompanyService companyService;
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    @Transactional
    public void registerUser(RegisterRequestDTO dto) {
        log.info("Iniciando registro do usuário: {}", dto.user().email());

        // 1. Valida
        userService.validateEmailAlreadyExists(dto.user().email());

        // 2. Cria empresa e usuário no banco primeiro
        User savedUser = cerateUserAndCompany(dto);

        // 3. Só agora cria no Keycloak
        String keycloakId = createUserOnKeycloak(dto);

        // 4. Atualiza o keycloakId no banco
        savedUser.setKeycloakId(UUID.fromString(keycloakId));
        userService.saveUser(savedUser);
        log.info("Usuário {} registrado com sucesso.", dto.user().email());
    }

    private User cerateUserAndCompany(RegisterRequestDTO dto) {
        userService.validateEmailAlreadyExists(dto.user().email());

        Company company = registerUserMapper.toCompany(dto);
        var newCompany = companyService.createCompany(company);
        User user = registerUserMapper.toUserRegister(dto, newCompany, passwordEncoder.encode(dto.user().password()));

        return this.userService.saveUser(user);

    }

    private String createUserOnKeycloak(RegisterRequestDTO dto) {
        UserRepresentation keycloakUser = getUserRepresentation(dto);

        Response response = keycloak.realm(realm).users().create(keycloakUser);

        if (response.getStatus() != 201) {
            log.warn("Erro ao criar usuário no Keycloak: {}", response.getStatus());
            throw new BusinessException("Erro ao criar usuário no Keycloak");
        }

        return CreatedResponseUtil.getCreatedId(response);
    }

    private UserRepresentation getUserRepresentation(RegisterRequestDTO dto) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(dto.user().password());
        credential.setTemporary(false);

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(dto.user().email());
        keycloakUser.setEmail(dto.user().email());
        keycloakUser.setFirstName(dto.user().name());
        keycloakUser.setLastName(dto.user().lastName());
        keycloakUser.setEnabled(true);
        keycloakUser.setCredentials(List.of(credential));
        return keycloakUser;
    }

}
