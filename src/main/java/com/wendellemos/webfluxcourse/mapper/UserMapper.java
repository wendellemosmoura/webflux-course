package com.wendellemos.webfluxcourse.mapper;

import com.wendellemos.webfluxcourse.entity.User;
import com.wendellemos.webfluxcourse.model.request.UserRequest;
import com.wendellemos.webfluxcourse.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest request);

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest request, @MappingTarget final User entity);

    UserResponse toResponse(final User entity);
}
