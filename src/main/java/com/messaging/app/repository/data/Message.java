package com.messaging.app.repository.data;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity()
@Table(name = "messages")
@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
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
    @EqualsAndHashCode.Exclude
    private Character status;
}
