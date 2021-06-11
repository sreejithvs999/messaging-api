package com.messaging.app;

import com.messaging.app.repository.MessageRepository;
import com.messaging.app.repository.UserRepository;
import com.messaging.app.services.MessageDTO;
import com.messaging.app.services.exceptions.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class SendMessageToUserTests extends BaseMessagingServiceAppTest {


    private static final String ENDPOINT_CREATE_MESSAGE = "/message";

    private static int USER_A_ID;
    private static int USER_B_ID;
    private static int USER_C_ID;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void beforeEach() {

        messageRepository.deleteAll();
        userRepository.deleteAll();

        USER_A_ID = createUser("User-A").getUserId();
        USER_B_ID = createUser("User-B").getUserId();
        USER_C_ID = createUser("User-C").getUserId();
    }

    @Test
    public void send_message_to_user__should_save_message_and_return_with_id() {

        //arrange
        var message = new MessageDTO();
        message.setSenderId(USER_A_ID);
        message.setReceiverId(USER_B_ID);
        var content = "Hello from A to B";
        message.setMessage(content);

        //act
        var response = restTemplate.postForEntity(ENDPOINT_CREATE_MESSAGE, message, MessageDTO.class);

        //assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(response.getBody().getMessageId()).isGreaterThan(0);
        Assertions.assertThat(response.getBody().getSenderId()).isEqualTo(USER_A_ID);
        Assertions.assertThat(response.getBody().getReceiverId()).isEqualTo(USER_B_ID);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo(content);
    }

    @Test
    public void send_message_to_user__when_receiver_not_exists__should_throw_error() {
        //arrange
        var message = new MessageDTO();
        message.setSenderId(USER_A_ID);
        message.setReceiverId(1000);
        var content = "Hello from A to ZZZZ";
        message.setMessage(content);

        //act
        var response = restTemplate.postForEntity(ENDPOINT_CREATE_MESSAGE, message, ErrorMessage.class);

        //assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("receiver not found. '1000'");

    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }
}
