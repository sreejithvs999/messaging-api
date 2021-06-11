package com.messaging.app;

import com.messaging.app.repository.MessageRepository;
import com.messaging.app.repository.UserRepository;
import com.messaging.app.services.MessageDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ReceiveMessagesTests extends BaseMessagingServiceAppTest {

    private static final String ENDPOINT_RECEIVED_MESSAGES = "/message?receiverId={receiverId}";
    private static final String ENDPOINT_SENT_MESSAGES = "/message?senderId={senderId}";
    private static final String ENDPOINT_RECEIVED_FROM_SENDER_MESSAGES = "/message?receiverId={receiverId}&senderId={senderId}";

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


        createMessage(USER_A_ID, USER_B_ID, "A to B 1");
        createMessage(USER_A_ID, USER_B_ID, "A to B 2");
        createMessage(USER_A_ID, USER_C_ID, "A to C 1");
        createMessage(USER_B_ID, USER_A_ID, "B to A 1");
        createMessage(USER_B_ID, USER_C_ID, "B to C 1");
        createMessage(USER_C_ID, USER_A_ID, "C to A 1");
        createMessage(USER_C_ID, USER_B_ID, "C to B 1");
        createMessage(USER_C_ID, USER_B_ID, "C to B 2");
    }


    @Test
    public void test_getUserReceivedMessages_return_all_received_messages_for_user() {

        var receivedByA = restTemplate.getForEntity(ENDPOINT_RECEIVED_MESSAGES, MessageDTO[].class, USER_A_ID);
        var receivedByB = restTemplate.getForEntity(ENDPOINT_RECEIVED_MESSAGES, MessageDTO[].class, USER_B_ID);
        var receivedByC = restTemplate.getForEntity(ENDPOINT_RECEIVED_MESSAGES, MessageDTO[].class, USER_C_ID);

        Assertions.assertThat(receivedByA.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(receivedByA.getBody())
                .extracting(m -> m.getMessage())
                .containsExactlyInAnyOrder("B to A 1", "C to A 1");

        Assertions.assertThat(receivedByB.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(receivedByB.getBody())
                .extracting(m -> m.getMessage())
                .containsExactlyInAnyOrder("A to B 1", "A to B 2", "C to B 1", "C to B 2");

        Assertions.assertThat(receivedByC.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(receivedByC.getBody())
                .extracting(m -> m.getMessage())
                .containsExactlyInAnyOrder("A to C 1", "B to C 1");

    }


    @Test
    public void test_getUserSentMessages__return_all_sent_messages_by_user() {

        var sentMessagesA = restTemplate.getForEntity(ENDPOINT_SENT_MESSAGES, MessageDTO[].class, USER_A_ID);
        Assertions.assertThat(sentMessagesA.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(sentMessagesA.getBody())
                .extracting(m -> m.getMessage())
                .containsExactlyInAnyOrder("A to B 1", "A to B 2", "A to C 1");

    }

    @Test
    public void test_getUserReceivedMessagesFromSender__return_all_recived_messages_by_sender() {

        var receivedByAfromC = restTemplate
                .getForEntity(ENDPOINT_RECEIVED_FROM_SENDER_MESSAGES, MessageDTO[].class,
                        USER_A_ID, USER_C_ID);
        Assertions.assertThat(receivedByAfromC.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(receivedByAfromC.getBody())
                .extracting(m -> m.getMessage())
                .containsExactlyInAnyOrder("C to A 1");
    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public MessageRepository getMessageRepository() {
        return messageRepository;
    }
}
