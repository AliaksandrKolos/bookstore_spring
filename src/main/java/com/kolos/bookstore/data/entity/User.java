package com.kolos.bookstore.data.entity;


import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;

    public enum Role {
        USER, MANAGER, ADMIN
    }
}