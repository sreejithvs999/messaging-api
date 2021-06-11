package com.messaging.app.services;

import com.messaging.app.repository.MessageRepository;
import com.messaging.app.repository.UserRepository;
import com.messaging.app.repository.data.Message;
import com.messaging.app.repository.data.User;
import com.messaging.app.services.exceptions.MessagingAppClientError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    public UserDTO createUser(UserDTO userDto) {

        userRepository.findByNickName(userDto.getNickName()).ifPresent(user -> {
            throw new MessagingAppClientError("NickName is already present. please choose another one.");
        });

        var user = new User();
        user.setNickName(userDto.getNickName());
        user = userRepository.save(user);
        userDto.setUserId(user.getUserId());
        return userDto;
    }

    public User resolveUser(String nickName) {

        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new MessagingAppClientError(String.format("User not found : %s", nickName)));
    }

    public MessageDTO createMessage(MessageDTO message) {

        val sender = getSender(message.getSenderId());
        val receiver = getReceiver(message.getReceiverId());

        if (sender.equals(receiver)) {
            throw new MessagingAppClientError("User cannot send message to him/herself.");
        }

        val messageEntity = messageRepository.save(
                Message.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .message(message.getMessage())
                        .timestamp(OffsetDateTime.now())
                        .status('A')
                        .build()
        );

        message.setMessageId(messageEntity.getMessageId());
        message.setTimestamp(messageEntity.getTimestamp());
        return message;
    }

    public List<MessageDTO> getUserReceivedMessages(Integer receiverId) {

        val receiver = getReceiver(receiverId);

        return messageRepository
                .findMessageByReceiver(receiver)
                .stream()
                .map(MessageDTO::from)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getUserSentMessages(Integer senderId) {

        val sender = getSender(senderId);

        return messageRepository
                .findMessageBySender(sender)
                .stream()
                .map(MessageDTO::from)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getUserReceivedMessagesFromSender(Integer receiverId, Integer senderId) {

        val sender = getSender(senderId);
        val receiver = getReceiver(receiverId);

        return messageRepository
                .findMessageBySenderAndReceiver(sender, receiver)
                .stream()
                .map(MessageDTO::from)
                .collect(Collectors.toList());
    }

    private User getSender(Integer senderId) {
        return getUser(senderId, String.format("sender not found. '%d'", senderId));
    }

    private User getReceiver(Integer receiverId) {
        return getUser(receiverId, String.format("receiver not found. '%d'", receiverId));
    }

    private User getUser(Integer userId, String notFoundMessage) {
        return userRepository.findById(userId).orElseThrow(() -> new MessagingAppClientError(notFoundMessage));
    }
}
