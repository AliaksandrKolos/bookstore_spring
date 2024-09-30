package com.kolos.bookstore.service;

import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntityRegistrationUser(UserRegistrationDto userRegistrationDto);

    UserDto toDtoRegistrationUser(User user);

    User toEntity(UserDto dto);

    UserDto toDto(User entity);
}
