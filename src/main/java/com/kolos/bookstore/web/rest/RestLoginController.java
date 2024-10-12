package com.kolos.bookstore.web.rest;

import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestLoginController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {
        return userService.verify(userDto);
    }

}
