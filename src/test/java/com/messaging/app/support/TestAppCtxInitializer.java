package com.messaging.app.support;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

import java.util.List;

public class TestAppCtxInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        postgreSQLContainer.setPortBindings(List.of("5432:5432"));
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
