package com.kolos.bookstore.service.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRegistrationDto {

    @Length(min = 3, max = 20, message = "min length 3 max length 20")
    private String password;
    @Email(message = "No valid email")
    private String email;
}
