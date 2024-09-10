package com.kolos.bookstore.service;


import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {

    UserDto get(Long id);

    List<UserDto> getAll(PageableDto pageableDto);

    UserDto create(UserDto dto);

    UserDto update(UserDto dto);

    void delete(Long id);

    UserDto registration(UserRegistrationDto userRegistrationDto);

    UserDto getByEmail(String email);

    List<UserDto> getByLastName(String lastName, PageableDto pageableDto);

    UserDto login(String email, String password);

}
