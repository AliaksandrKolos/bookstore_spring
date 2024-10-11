package com.kolos.bookstore.integration.service;

import com.kolos.bookstore.annotation.IT;
import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.repository.UserRepository;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserServiceIT {

    private final UserService userService;
    private final static Long ID = 1L;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void getAllUsers() {

        UserDto user1 = userService.get(1L);
        UserDto user2 = userService.get(2L);
        UserDto user3 = userService.get(3L);
        UserDto user4 = userService.get(4L);
        UserDto user5 = userService.get(5L);

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id"));
        Page<UserDto> users = userService.getAll(pageable);

        assertNotNull(users);
        assertEquals(20, users.getTotalElements());

        List<UserDto> userList = users.getContent();
        assertEquals(user1.getEmail(), userList.get(0).getEmail());
        assertEquals(user2.getEmail(), userList.get(1).getEmail());
        assertEquals(user3.getEmail(), userList.get(2).getEmail());
        assertEquals(user4.getEmail(), userList.get(3).getEmail());
        assertEquals(user5.getEmail(), userList.get(4).getEmail());

    }

    @Test
    void getUser() {
        Optional<User> maybeUser = userRepository.findById(ID);
        assertNotNull(maybeUser);
        maybeUser.ifPresent(user -> assertEquals("admin", user.getEmail()));
    }

    @Test
    void updateUser() {

        UserDto userDto = userService.get(ID);
        String oldEmail = userDto.getEmail();

        UserDto actualResult = userService.update(userDto);
        actualResult.setEmail("test"+UUID.randomUUID()+"@mail.ru");
        assertNotNull(actualResult);

        assertEquals(userDto.getFirstName(), actualResult.getFirstName());
        assertEquals(userDto.getLastName(), actualResult.getLastName());
        assertTrue(bCryptPasswordEncoder.matches(userDto.getPassword(), actualResult.getPassword()));
        assertNotEquals(oldEmail, actualResult.getEmail());
        assertEquals(userDto.getRole(), actualResult.getRole());

    }

}
