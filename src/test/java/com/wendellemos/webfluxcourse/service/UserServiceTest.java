package com.wendellemos.webfluxcourse.service;

import com.wendellemos.webfluxcourse.entity.User;
import com.wendellemos.webfluxcourse.mapper.UserMapper;
import com.wendellemos.webfluxcourse.model.request.UserRequest;
import com.wendellemos.webfluxcourse.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void save() {
        UserRequest request = new UserRequest("User", "user@test.com", "123456");
        User entity = User.builder().build();

        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.save(request);

        StepVerifier.create(result)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
    }
}
