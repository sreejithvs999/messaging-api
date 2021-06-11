package com.messaging.app.services;

import com.messaging.app.repository.data.Message;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Integer messageId;

    private Integer senderId;

    private Integer receiverId;

    private String message;

    private OffsetDateTime timestamp;

    public static MessageDTO from(Message message) {
        return MessageDTO.builder()
                .messageId(message.getMessageId())
                .message(message.getMessage())
                .receiverId(message.getReceiver().getUserId())
                .senderId(message.getSender().getUserId())
                .timestamp(message.getTimestamp())
                .build();

    }
}
