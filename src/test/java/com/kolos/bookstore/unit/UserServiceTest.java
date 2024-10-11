package com.kolos.bookstore.unit;


import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.repository.UserRepository;
import com.kolos.bookstore.service.UserMapper;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import com.kolos.bookstore.service.exception.UserInputValidationException;
import com.kolos.bookstore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Long ID = 1L;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getById() {
        User user = new User();
        user.setId(ID);
        UserDto expectedResult = new UserDto();
        expectedResult.setId(ID);

        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(ID);
        Mockito.doReturn(expectedResult).when(userMapper).toDto(user);

        var actualResult = userService.get(ID);

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAll() {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        List<User> users = List.of(user1, user2);
        Page<User> usersPage = new PageImpl<>(users);

        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setEmail("email@email1.com");

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("email@email2.com");

        when(userRepository.findAll(any(Pageable.class))).thenReturn(usersPage);
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<UserDto> result = userService.getAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("email@email1.com", result.getContent().get(0).getEmail());
        assertEquals("email@email2.com", result.getContent().get(1).getEmail());

        verify(userRepository, times(1)).findAll(pageable);
        verify(userMapper, times(1)).toDto(user1);
        verify(userMapper, times(1)).toDto(user2);
    }

    @Test
    void registrationUser_whenUserDoesNotExist_shouldRegistrationUser() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("123@mail.com");
        registrationDto.setPassword("12345");

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
        user.setRole(User.Role.USER);

        UserDto expectedResult = new UserDto();
        expectedResult.setEmail(user.getEmail());

        when(userRepository.findByEmail(registrationDto.getEmail())).thenReturn(Optional.empty());
        when(userMapper.toEntityRegistrationUser(registrationDto)).thenReturn(user);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDtoRegistrationUser(user)).thenReturn(expectedResult);

        UserDto result = userService.registration(registrationDto);

        assertNotNull(result);
        assertEquals(expectedResult.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(registrationDto.getEmail());
        verify(userMapper, times(1)).toEntityRegistrationUser(registrationDto);
        verify(bCryptPasswordEncoder, times(1)).encode(registrationDto.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDtoRegistrationUser(user);
    }


    @Test
    void createBook_whenBookAlreadyExists_shouldThrowException() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("123@mail.com");
        registrationDto.setPassword("12345");

        User existingUser = new User();
        existingUser.setEmail(registrationDto.getEmail());

        when(userRepository.findByEmail(registrationDto.getEmail())).thenReturn(Optional.of(existingUser));
        when(messageSource.getMessage("user.emailAlreadyInUse", new Object[0], LocaleContextHolder.getLocale()))
                .thenReturn("Email is already in use");

        UserInputValidationException exception = assertThrows(UserInputValidationException.class, () -> {
            userService.registration(registrationDto);
        });

        assertEquals("Email is already in use", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(registrationDto.getEmail());
        verify(userMapper, never()).toEntityRegistrationUser(any());
        verify(bCryptPasswordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toDtoRegistrationUser(any());
    }

}
