package com.saj.api.shared.notifications.services;

import com.saj.api.modules.process.domain.entities.ProcessMovements;
import com.saj.api.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendMovementNotification(ProcessMovements movement) {
        var process = movement.getProcess();
        var user = process.getClient();
        if (user == null || user.getEmail() == null) {
            log.warn("Destinatário não definido para o processo: {}", process.getNumberProcess());
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Nova movimentação - Processo " + process.getNumberProcess());
        message.setText("""
                Olá %s,
                
                Uma nova movimentação foi registrada:
                
                Processo: %s
                Título: %s
                Descrição: %s
                Data: %s
                
                Acesse o sistema para mais detalhes.
                """.formatted(
                user.getName(),
                process.getNumberProcess(),
                movement.getTitle(),
                movement.getDescription(),
                movement.getDateEvent()
        ));
        mailSender.send(message);
        log.info("E-mail enviado para: {}", user.getEmail());
    }
}
