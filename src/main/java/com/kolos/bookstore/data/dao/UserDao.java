package com.kolos.bookstore.data.dao;

import com.kolos.bookstore.data.dto.UserDto;

import java.util.List;

public interface UserDao extends AbstractDao<Long, UserDto> {

    UserDto findByEmail(String email);

    List<UserDto> findByLastName(String lastName, int limit, int offset);

    int countAll();

}
