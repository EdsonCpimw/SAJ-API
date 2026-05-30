package com.saj.api.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ObjectNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ObjectNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }

    public ObjectNotFoundException(String message) {
      this(message, HttpStatus.NOT_FOUND);
    }
}
