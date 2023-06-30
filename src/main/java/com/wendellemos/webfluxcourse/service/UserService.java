package com.wendellemos.webfluxcourse.service;

import com.wendellemos.webfluxcourse.entity.User;
import com.wendellemos.webfluxcourse.mapper.UserMapper;
import com.wendellemos.webfluxcourse.model.request.UserRequest;
import com.wendellemos.webfluxcourse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public Mono<User> save(final UserRequest request) {
       return userRepository.save(userMapper.toEntity(request));
    }

    public Mono<User> findById(final String id) {
        return userRepository.findById(id);
    }
}
