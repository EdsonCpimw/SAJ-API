package com.saj.api.modules.auth.service;

import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
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
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public String createUserOnKeycloak(RegisterRequestDTO dto) {
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

    public void sendResetPasswordEmail(UUID keycloakId) {
        try {
            keycloak.realm(realm)
                    .users()
                    .get(keycloakId.toString())
                    .executeActionsEmail(List.of("UPDATE_PASSWORD"));
            log.info("Email de reset enviado. keycloakId: {}", keycloakId);
        } catch (Exception ex) {
            log.error("Erro ao enviar email de reset. keycloakId: {}", keycloakId, ex);
            throw new BusinessException("Erro ao processar a solicitação de reset de senha");
        }
    }

    public void updatePassword(UUID keycloacId, String newPassword) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);

        keycloak.realm(realm)
                .users()
                .get(keycloacId.toString())
                .resetPassword(credential);

        log.info("Senha atualizada no Keycloak. keycloakId: {}", keycloacId);
    }

}
