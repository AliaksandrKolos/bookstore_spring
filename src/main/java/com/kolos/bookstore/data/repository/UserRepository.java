package com.kolos.bookstore.data.repository;

import com.kolos.bookstore.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Override
    @Query("FROM User u WHERE u.id = :id AND u.deleted = false")
    Optional<User> findById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :id")
    void deleteById(Long id);

    @Query("FROM User u WHERE u.email = :email AND u.deleted = false")
    Optional<User> findByEmail(String email);

    @Query("FROM User u WHERE LOWER(u.lastName) = LOWER(:lastName) AND u.deleted = false")
    Page<User> findAllByLastName(String lastName, Pageable pageable);

    @Override
    @Query("FROM User u WHERE u.deleted = false")
    Page<User> findAll(Pageable pageable);
}