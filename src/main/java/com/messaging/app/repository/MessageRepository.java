package com.messaging.app.repository;

import com.messaging.app.repository.data.Message;
import com.messaging.app.repository.data.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    Collection<Message> findMessageBySender(User sender);

    Collection<Message> findMessageByReceiver(User receiver);

    Collection<Message> findMessageBySenderAndReceiver(User sender, User receiver);
}
