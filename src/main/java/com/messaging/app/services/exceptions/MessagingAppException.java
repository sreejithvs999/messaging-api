package com.messaging.app.services.exceptions;

import org.springframework.http.HttpStatus;

public abstract class MessagingAppException extends RuntimeException {

    public MessagingAppException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
