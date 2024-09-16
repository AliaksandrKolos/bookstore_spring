package com.kolos.bookstore.data.entity.converters;

import com.kolos.bookstore.data.entity.User;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<User.Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(User.Role role) {

        if (role == null) {
            return null;
        }


        return switch (role) {
            case ADMIN -> 1;
            case MANAGER -> 2;
            case USER -> 3;
        };
    }

    @Override
    public User.Role convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return switch (integer) {
            case 1 -> User.Role.ADMIN ;
            case 2 -> User.Role.MANAGER;
            case 3 -> User.Role.USER;
            default -> throw new IllegalArgumentException("Invalid role id " + integer);
        };
    }
}
