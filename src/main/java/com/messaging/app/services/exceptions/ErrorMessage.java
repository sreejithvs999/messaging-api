package com.messaging.app.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorMessage {

    private String message;

    public static ErrorMessage of(String message) {
        return new ErrorMessage(message);
    }
}
