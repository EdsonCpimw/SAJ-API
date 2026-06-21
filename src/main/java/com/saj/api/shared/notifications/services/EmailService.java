package com.saj.api.shared.notifications.services;

import com.saj.api.modules.process.domain.entities.ProcessMovements;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");


    public void sendMovementNotification(ProcessMovements movement) {
        var process = movement.getProcess();
        var userClient = process.getClient();
        if (userClient == null || userClient.getEmail() == null) {
            log.warn("Destinatário não definido para o processo: {}", process.getNumberProcess());
            return;
        }

        Context context = new Context();
        context.setVariable("clientName", userClient.getName());
        context.setVariable("processNumber", process.getNumberProcess());
        context.setVariable("movementTitle", movement.getTitle());
        context.setVariable("movementDescription", movement.getDescription());
        context.setVariable("eventDate", movement.getDateEvent() != null
                ? movement.getDateEvent().format(DATE_FORMATTER)
                : null);
        context.setVariable("systemUrl", "html://localhost:9001/process/" + process.getId() + "/movements");

        String htmlBody = templateEngine.process("emails/movement-notification", context);

        sendHtmlEmail(userClient.getEmail(), "Nova movimentação - Processo " + process.getNumberProcess(), htmlBody);

    }

    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);
            log.info("Email enviado para: {}", to);

        } catch (MessagingException ex) {
            log.error("Erro ao enviar email pra: {}", to, ex);
        }
    }
}
