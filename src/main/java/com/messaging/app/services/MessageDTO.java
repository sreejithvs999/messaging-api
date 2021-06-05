package com.messaging.app.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Data
public class MessageDTO {

    private Integer messageId;

    private String senderId;

    private String receiverId;

    private String message;

    private OffsetDateTime timestamp;
}
