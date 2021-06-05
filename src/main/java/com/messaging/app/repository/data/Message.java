package com.messaging.app.repository.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity()
@Getter
@Setter
@Table(name = "messages")
@Data
public class Message {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private OffsetDateTime timestamp;

    @Column(name = "status")
    private Character status;
}
