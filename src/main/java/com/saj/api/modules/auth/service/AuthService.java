package com.saj.api.modules.auth.service;

import com.saj.api.modules.auth.controller.dtos.AuthenticatedUser;
import com.saj.api.modules.auth.controller.dtos.ResetPasswordDTO;
import com.saj.api.modules.auth.domain.entities.PasswordResetToken;
import com.saj.api.modules.auth.infrastructure.repository.PasswordResetTokenRepository;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.service.UserService;
import com.saj.api.shared.exceptions.BusinessException;
import com.saj.api.shared.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final KeycloakService keycloakService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    private AuthenticatedUser getAuthenticatedUser() {
        Jwt jwt = getJwt();
        return new AuthenticatedUser(
                jwt.getSubject(),
                jwt.getClaimAsString("name"),
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("give_name"),
                jwt.getClaimAsString("family_name")
        );
    }

    public String getAuthenticatedUserEmail() {
        AuthenticatedUser user = getAuthenticatedUser();
        return user.email();
    }

    public User getCurrentUser() {
        String authenticatedEmail = getAuthenticatedUserEmail();
        return userService.findUserByEmail(authenticatedEmail);

    }

    private static Jwt getJwt() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwtToken = (JwtAuthenticationToken) authentication;
        return jwtToken.getToken();
    }

    @Transactional
    public void forgotPassword(String email) {
        log.info("Iniciando a solicitação de reset de senha para o email: {}", email);

        var userOptional = userService.getUserByEmail(email);

        if (userOptional.isEmpty()) {
            log.warn("Tetantiva de reset para email não encontrado: {}", email);
            return;
        }
        User user = userOptional.get();

        passwordResetTokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .used(false)
                .build();
        passwordResetTokenRepository.save(resetToken);

        emailService.sendResetPasswordEmail(user, token);

        log.info("Email de reset enviado via keycloak para: {}", email);
    }

    public void resetPassword(ResetPasswordDTO dto) {
        log.info("Processando rest de senha...");

        if (!dto.password().equals(dto.confirmPassword())) {
            throw new BusinessException("Senhas não conferem");
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(dto.token()).orElseThrow(() -> {
            log.warn("Token de reset inválido: {}", dto.token());
            return new BusinessException("Token inválido ou expirado");
        });

        if (resetToken.isExpired()) {
            log.warn("Token de reset expirado: {}", dto.token());
            throw new BusinessException("Token já utilizado - solicite um novo reset");
        }

        if (resetToken.isUsed()) {
            log.warn("Token de reset já utilizado: {}", dto.token());
        }

        keycloakService.updatePassword(resetToken.getUser().getKeycloakId(), dto.password());

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        log.info("Senha resetada com sucesso para usuário: {}", resetToken.getUser().getEmail());
    }
}
