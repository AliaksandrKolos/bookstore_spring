package com.kolos.bookstore.controller.filter;

import com.kolos.bookstore.service.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

public enum CommandSecurityList {
    INSTANCE;

    private final Map<String, UserDto.Role> securityConfig = new HashMap<>();

    CommandSecurityList() {

        // admin level
        securityConfig.put("userEditCommand", UserDto.Role.ADMIN);
        securityConfig.put("userEditFormCommand", UserDto.Role.ADMIN);
        securityConfig.put("userDeleteCommand", UserDto.Role.ADMIN);
        securityConfig.put("userCreateFormCommand", UserDto.Role.ADMIN);
        securityConfig.put("userCreateCommand", UserDto.Role.ADMIN);


        // manager level
        securityConfig.put("bookCreateFormCommand", UserDto.Role.MANAGER);
        securityConfig.put("bookCreateCommand", UserDto.Role.MANAGER);
        securityConfig.put("bookEditCommand", UserDto.Role.MANAGER);
        securityConfig.put("bookEditFormCommand", UserDto.Role.MANAGER);
        securityConfig.put("bookDeleteCommand", UserDto.Role.MANAGER);
        securityConfig.put("usersCommand", UserDto.Role.MANAGER);
        securityConfig.put("orders", UserDto.Role.MANAGER);
        securityConfig.put("orderChangeStatus", UserDto.Role.MANAGER);
        securityConfig.put("userSearchByLastName", UserDto.Role.MANAGER);

        // user level
        securityConfig.put("logOutCommand", UserDto.Role.USER);
        securityConfig.put("orderCommand", UserDto.Role.USER);
        securityConfig.put("ordersUserCommand", UserDto.Role.USER);
        securityConfig.put("orderCancelCommand", UserDto.Role.USER);
        securityConfig.put("orderCreateCommand", UserDto.Role.USER);
        securityConfig.put("userCommand", UserDto.Role.USER);
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
