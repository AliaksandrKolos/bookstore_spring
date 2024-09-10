package com.kolos.bookstore.controller.filter;

import com.kolos.bookstore.service.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

public enum CommandSecurityList {
    INSTANCE;

    private final Map<String, UserDto.Role> securityConfig = new HashMap<>();

    CommandSecurityList() {

        // admin level
        securityConfig.put("user_edit", UserDto.Role.ADMIN);
        securityConfig.put("user_edit_form", UserDto.Role.ADMIN);
        securityConfig.put("user_delete", UserDto.Role.ADMIN);

        // manager level
        securityConfig.put("book_create_form", UserDto.Role.MANAGER);
        securityConfig.put("book_create", UserDto.Role.MANAGER);
        securityConfig.put("book_edit", UserDto.Role.MANAGER);
        securityConfig.put("book_edit_form", UserDto.Role.MANAGER);
        securityConfig.put("book_delete", UserDto.Role.MANAGER);
        securityConfig.put("users", UserDto.Role.MANAGER);
        securityConfig.put("user_create", UserDto.Role.MANAGER);
        securityConfig.put("user_create_form", UserDto.Role.MANAGER);
        securityConfig.put("orders", UserDto.Role.MANAGER);
        securityConfig.put("change_order_status", UserDto.Role.MANAGER);
        securityConfig.put("users_search_last_name", UserDto.Role.MANAGER);

        // user level
        securityConfig.put("user_logOut", UserDto.Role.USER);
        securityConfig.put("order", UserDto.Role.USER);
        securityConfig.put("orders_user", UserDto.Role.USER);
        securityConfig.put("order_cancel", UserDto.Role.USER);
        securityConfig.put("create_order", UserDto.Role.USER);
        securityConfig.put("user", UserDto.Role.USER);

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
