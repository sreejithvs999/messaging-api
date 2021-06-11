package com.messaging.app.repository;

import com.messaging.app.repository.data.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByNickName(String nickName);
}
