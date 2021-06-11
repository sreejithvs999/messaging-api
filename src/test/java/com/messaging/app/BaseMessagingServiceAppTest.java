package com.messaging.app;

import com.messaging.app.repository.MessageRepository;
import com.messaging.app.repository.UserRepository;
import com.messaging.app.repository.data.Message;
import com.messaging.app.repository.data.User;
import com.messaging.app.support.TestAppCtxInitializer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {TestAppCtxInitializer.class})
public abstract class BaseMessagingServiceAppTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    protected User createUser(String nickName) {
        var user = new User(null, nickName);
        return getUserRepository().save(user);
    }

    protected Message createMessage(int senderId, int receiverId, String content) {
        var message = Message.builder()
                .sender(getUserRepository().findById(senderId).orElseThrow())
                .receiver(getUserRepository().findById(receiverId).orElseThrow())
                .status('A')
                .message(content)
                .timestamp(OffsetDateTime.now())
                .build();
        return getMessageRepository().save(message);
    }

    protected UserRepository getUserRepository() {
        throw new AssertionError("no userRepository bean");
    }

    protected MessageRepository getMessageRepository() {
        throw new AssertionError("no messageRepository bean");
    }
}
