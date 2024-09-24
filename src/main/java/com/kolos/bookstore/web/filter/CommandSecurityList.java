package com.kolos.bookstore.web.filter;

import com.kolos.bookstore.service.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

public enum CommandSecurityList {
    INSTANCE;

    private final Map<String, UserDto.Role> securityConfig = new HashMap<>();

    CommandSecurityList() {

        // admin level
        securityConfig.put("/users/edit", UserDto.Role.ADMIN);
        securityConfig.put("/users/delete/{id:\\d+}", UserDto.Role.ADMIN);

        // manager level
        securityConfig.put("books/create", UserDto.Role.MANAGER);
        securityConfig.put("/books/edit", UserDto.Role.MANAGER);
        securityConfig.put("/books/delete/{id:\\d+}", UserDto.Role.MANAGER);
        securityConfig.put("/users/getAll", UserDto.Role.MANAGER);
        securityConfig.put("/users/create", UserDto.Role.MANAGER);
        securityConfig.put("/orders/getAll", UserDto.Role.MANAGER);
        securityConfig.put("/orders/change_status", UserDto.Role.MANAGER);
        securityConfig.put("/users/search_lastName", UserDto.Role.MANAGER);

        // user level
        securityConfig.put("/logOut", UserDto.Role.USER);
        securityConfig.put("/orders/{id:\\d+}", UserDto.Role.USER);
        securityConfig.put("/orders/orders_user/{id:\\d+}", UserDto.Role.USER);
        securityConfig.put("/orders/cancelOrder/{id:\\d+}", UserDto.Role.USER);
        securityConfig.put("/orders/order/create", UserDto.Role.USER);
        securityConfig.put("/users/{id:\\d+}", UserDto.Role.USER);

    }

    public boolean isCommandAllowedForRole(String command, UserDto.Role role) {
        UserDto.Role requiredRole = securityConfig.get(command);

        if (requiredRole == null) {
            return false;
        }

        if (role.equals(UserDto.Role.ADMIN)) {
            return true;
        } else if (role.equals(UserDto.Role.MANAGER)) {
            return requiredRole.equals(UserDto.Role.MANAGER) || requiredRole.equals(UserDto.Role.USER);
        } else {
            return requiredRole.equals(UserDto.Role.USER);
        }
    }
    public boolean isRestricted(String command) {
        return securityConfig.get(command) != null;
    }
}
