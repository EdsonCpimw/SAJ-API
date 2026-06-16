package com.saj.api.shared.notifications.listeners;

import com.saj.api.modules.process.domain.events.MovementCreatedEvent;
import com.saj.api.shared.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovementNotificationListener {

    private final EmailService emailService;

    @Async
    @EventListener
    public void onMovementCreated(MovementCreatedEvent event) {
        var movement = event.getMovement();
        log.info("[async] Processndo notificação. movementId: {}", movement.getId());
        try{
            emailService.sendMovementNotification(movement);
        } catch (Exception ex) {
            log.error("Erro ao enviar notificação. movementId: {} Erro: {}", movement.getId(), ex.getMessage());
        }
    }
}
