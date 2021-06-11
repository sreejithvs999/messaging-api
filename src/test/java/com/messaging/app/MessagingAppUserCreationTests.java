package com.messaging.app;

import com.messaging.app.repository.UserRepository;
import com.messaging.app.repository.data.User;
import com.messaging.app.services.UserDTO;
import com.messaging.app.services.exceptions.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class MessagingAppUserCreationTests extends BaseMessagingServiceAppTest {


    private static final String USER_A_NICKNAME = "User-A";

    private static final String ENDPOINT_CREATE_USER = "/user";
    private static final String ENDPOINT_RESOLVE_USER = "/user?nickName={nickName}";

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    public void test__create_user__return_user_id() {

        //act
        var user = new UserDTO();
        user.setNickName(USER_A_NICKNAME);
        var response = restTemplate.postForEntity(ENDPOINT_CREATE_USER, user, UserDTO.class);

        //assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody().getNickName()).isEqualTo(USER_A_NICKNAME);
        Assertions.assertThat(response.getBody().getUserId()).isGreaterThan(0);
    }


    @Test
    public void test__create_user__when_duplicate_nickname__throw_error() {

        //arrange
        var user = new UserDTO();
        user.setNickName(USER_A_NICKNAME);
        restTemplate.postForEntity(ENDPOINT_CREATE_USER, user, User.class);

        //act
        var response = restTemplate.postForEntity(ENDPOINT_CREATE_USER, user, ErrorMessage.class);

        //assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("NickName is already present. please choose another one.");
    }

    @Test
    public void test__resolve_user__return_user_with_id() {

        //arrange
        var user = new UserDTO();
        user.setNickName(USER_A_NICKNAME);
        var response = restTemplate.postForEntity(ENDPOINT_CREATE_USER, user, UserDTO.class);
        var expectedUserId = response.getBody().getUserId();

        //act
        response = restTemplate.getForEntity(ENDPOINT_RESOLVE_USER, UserDTO.class, USER_A_NICKNAME);

        //assert
        Assertions.assertThat(response.getBody().getNickName()).isEqualTo(USER_A_NICKNAME);
        Assertions.assertThat(response.getBody().getUserId()).isEqualTo(expectedUserId);
    }
}
