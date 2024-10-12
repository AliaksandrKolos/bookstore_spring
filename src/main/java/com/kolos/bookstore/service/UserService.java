package com.kolos.bookstore.service;


import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto get(Long id);

    Page<UserDto> getAll(Pageable pageable);

    UserDto create(UserDto dto);

    UserDto update(UserDto dto);

    void delete(Long id);

    UserDto registration(UserRegistrationDto userRegistrationDto);

    UserDto getByEmail(String email);

    Page<UserDto> getByLastName(String lastName, Pageable pageable);


    String verify(UserDto userDto);
}
