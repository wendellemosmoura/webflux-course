package com.wendellemos.webfluxcourse.service;

import com.wendellemos.webfluxcourse.entity.User;
import com.wendellemos.webfluxcourse.mapper.UserMapper;
import com.wendellemos.webfluxcourse.model.request.UserRequest;
import com.wendellemos.webfluxcourse.repository.UserRepository;
import com.wendellemos.webfluxcourse.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public Mono<User> save(final UserRequest request) {
       return userRepository.save(userMapper.toEntity(request));
    }

    public Mono<User> findById(final String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException(
                        format("Object not found. Id: %s, Type: %s", id, User.class.getSimpleName())
                )));
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }
}
