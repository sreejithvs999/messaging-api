package com.messaging.app;

import com.messaging.app.services.MessageDTO;
import com.messaging.app.web.domain.web.AppController;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {MessagingServiceAppTest.Initializer.class})
public class MessagingServiceAppTest {

    @Autowired
    private AppController appController;

    private static final String USER_A = "user-a";
    private static final String USER_B = "user-b";
    private static final String USER_C = "user-c";

    @Test
    public void test_getUserReceivedMessages() {

        appController.createUser(USER_A);
        appController.createUser(USER_B);
        appController.createUser(USER_C);


        var message = new MessageDTO();
        message.setSenderId(USER_A);
        message.setReceiverId(USER_B);
        message.setMessage("Message from A to B");
        appController.createMessage(message);

        message = new MessageDTO();
        message.setSenderId(USER_C);
        message.setReceiverId(USER_B);
        message.setMessage("Message from C to B");
        appController.createMessage(message);

        var messages = appController.getUserReceivedMessages(USER_B);

        Assertions.assertThat(messages.size()).isEqualTo(2);
        Assertions.assertThat(messages)
                .filteredOn(m -> m.getSenderId().equals(USER_A)).isNotEmpty()
                .map(m -> m.getMessage()).isEqualTo("Message from A to B");
        Assertions.assertThat(messages)
                .filteredOn(m -> m.getSenderId().equals(USER_C)).isNotEmpty()
                .map(m -> m.getMessage()).isEqualTo("Message from C to B");
    }


    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            postgreSQLContainer.waitingFor(new HostPortWaitStrategy());
            postgreSQLContainer.start();

            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.datasource.driver-class-name=org.postgresql.Driver",
                    "spring.jpa.generate-ddl=true",
                    "spring.jpa.hibernate.ddl-auto=create-drop"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
