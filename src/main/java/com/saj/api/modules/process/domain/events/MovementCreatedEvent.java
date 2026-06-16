package com.saj.api.modules.process.domain.events;

import com.saj.api.modules.process.domain.entities.ProcessMovements;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MovementCreatedEvent extends ApplicationEvent {

    private final ProcessMovements movement;

    public MovementCreatedEvent(ProcessMovements movement) {
        super(movement);
        this.movement = movement;
    }
}
