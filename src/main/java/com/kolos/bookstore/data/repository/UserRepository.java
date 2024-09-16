package com.kolos.bookstore.data.repository;

import com.kolos.bookstore.data.entity.User;

import java.util.List;

public interface UserRepository extends AbstractRepository<Long, User> {

    User findByEmail(String email);

    List<User> findByLastName(String lastName, long limit, long offset);

    long countAll();

}
