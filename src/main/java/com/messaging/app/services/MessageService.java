package com.messaging.app.services;

import com.messaging.app.repository.MessageRepository;
import com.messaging.app.repository.UserRepository;
import com.messaging.app.repository.data.Message;
import com.messaging.app.repository.data.User;
import com.messaging.app.services.exceptions.MessagingAppException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public User createUser(String userId) {

        userRepository.findById(userId).ifPresent(user -> {
            throw new MessagingAppException("userId is already registered");
        });

        val user = new User();
        user.setUserId(userId);

        return userRepository.save(user);
    }

    public MessageDTO createMessage(MessageDTO message) {

        val sender = getSender(message.getSenderId());
        val receiver = getReceiver(message.getReceiverId());

        var messageEntity = new Message();
        messageEntity.setMessage(message.getMessage());
        messageEntity.setSender(sender);
        messageEntity.setReceiver(receiver);
        messageEntity.setTimestamp(OffsetDateTime.now());
        messageEntity.setStatus('A');
        messageEntity = messageRepository.save(messageEntity);

        message.setMessageId(messageEntity.getMessageId());
        message.setTimestamp(messageEntity.getTimestamp());
        return message;
    }

    public List<MessageDTO> getUserReceivedMessages(String receiverId) {

        val receiver = getReceiver(receiverId);

        return messageRepository
                .findMessageByReceiver(receiver)
                .stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getUserSentMessages(String senderId) {

        val sender = getSender(senderId);

        return messageRepository
                .findMessageBySender(sender)
                .stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getUserReceivedMessagesFromSender(String receiverId, String senderId) {

        val sender = getSender(senderId);
        val receiver = getReceiver(receiverId);

        return messageRepository
                .findMessageBySenderAndReceiver(receiver, sender)
                .stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toList());
    }

    private User getSender(String senderId) {
        return getUser(senderId, "sender not found");
    }

    private User getReceiver(String receiverId) {
        return getUser(receiverId, "sender not found");
    }

    private User getUser(String userId, String fallbackErrorMsg) {
        return userRepository.findById(userId).orElseThrow(() -> new MessagingAppException(fallbackErrorMsg));
    }

    private MessageDTO mapToMessageDTO(Message m) {
        val message = new MessageDTO();
        message.setMessageId(m.getMessageId());
        message.setMessage(m.getMessage());
        message.setReceiverId(m.getReceiver().getUserId());
        message.setSenderId(m.getSender().getUserId());
        message.setTimestamp(m.getTimestamp());
        return message;
    }
}
