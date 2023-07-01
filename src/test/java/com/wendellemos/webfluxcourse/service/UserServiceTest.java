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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void testSave() {
        UserRequest request = new UserRequest("User", "user@test.com", "123456");
        User entity = User.builder().build();

        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.save(request);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindById() {
        when(userRepository.findById(anyString())).thenReturn(Mono.just(User.builder().id("1234").build()));

        Mono<User> result = userService.findById("1234");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Flux.just(User.builder().build()));

        Flux<User> result = userService.findAll();

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();
    }

    @Test
    void testUpdate() {
        UserRequest request = new UserRequest("User", "user@test.com", "123456");
        User entity = User.builder().build();

        when(userMapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(entity);
        when(userRepository.findById(anyString())).thenReturn(Mono.just(entity));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(entity));

        Mono<User> result = userService.update("123", request);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();
    }

    @Test
    void testDelete() {
        User entity = User.builder().build();
        when(userRepository.findAndRemove(anyString())).thenReturn(Mono.just(entity));

        Mono<User> result = userService.delete("123");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();
    }
}
