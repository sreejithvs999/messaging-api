package com.messaging.app.services.exceptions;

import org.springframework.http.HttpStatus;

public class MessagingAppClientError extends MessagingAppException {

    public MessagingAppClientError(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
