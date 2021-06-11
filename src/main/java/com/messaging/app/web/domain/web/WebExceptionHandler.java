package com.messaging.app.web.domain.web;

import com.messaging.app.services.exceptions.ErrorMessage;
import com.messaging.app.services.exceptions.MessagingAppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(MessagingAppException.class)
    public ResponseEntity<ErrorMessage> handleClientErrors(MessagingAppException e) {

        return ResponseEntity.status(e.getHttpStatus()).body(ErrorMessage.of(e.getMessage()));
    }
}
