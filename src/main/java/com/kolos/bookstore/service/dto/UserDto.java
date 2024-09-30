package com.kolos.bookstore.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    @NotBlank
    @Length(min = 5, max = 50, message = "min length 6 simbl, max length 50")
    private String password;
    @NotNull(message = "Should be not null")
    @Email(message = "Email is not valid")
    private String email;
    private Role role;

    public enum Role {
        USER, MANAGER, ADMIN
    }
}
