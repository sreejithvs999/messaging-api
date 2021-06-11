package com.messaging.app.services.exceptions;

import org.springframework.http.HttpStatus;

public class MessagingAppServerError extends MessagingAppException {

    public MessagingAppServerError(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
