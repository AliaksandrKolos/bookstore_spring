package com.kolos.bookstore.service.impl;

import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.repository.UserRepository;
import com.kolos.bookstore.service.UserMapper;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import com.kolos.bookstore.service.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Transactional
    @Override
    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        log.info("UserRegistrationDto: {}", userRegistrationDto);
        validation(userRegistrationDto);
        User user = userMapper.toEntityRegistrationUser(userRegistrationDto);
        user.setRole(User.Role.USER);
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
        User userRegistration = userRepository.save(user);
        log.debug("Calling create {}", userRegistration);
        return userMapper.toDtoRegistrationUser(user);
    }


    @Override
    public UserDto get(Long id) {
        log.debug("Calling getById {}", id);
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage("user.notFoundUser", new Object[0], LocaleContextHolder.getLocale())));
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        log.debug("Colling getAll");
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> getByLastName(String lastName, Pageable pageable) {
        log.debug("Calling getByLastName {}", lastName);
        return userRepository.findAllByLastName(lastName, pageable)
                .map(userMapper::toDto);
    }

    @Override
    public String verify(UserDto userDto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDto.getEmail());
        } else {
            return "Invalid email or password";
        }
    }

    @Transactional
    @Override
    public UserDto create(UserDto dto) {
        String emailToBeSaved = dto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved).orElse(null);
        if (byEmail != null) {
            throw new UserInputValidationException(messageSource.getMessage("user.emailExists", new Object[0], LocaleContextHolder.getLocale()));
        }
        User user = userMapper.toEntity(dto);
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        User created = userRepository.save(user);
        return userMapper.toDto(created);
    }

    @Transactional
    @Override
    public UserDto update(UserDto dto) {
        String emailToBeSaved = dto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved).orElse(null);
        if (byEmail != null && !byEmail.getId().equals(dto.getId())) {
            throw new UpdateFailedException(messageSource.getMessage("user.emailExists", new Object[0], LocaleContextHolder.getLocale()));
        }
        log.debug("Calling update");
        User user = userMapper.toEntity(dto);
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.debug("Calling delete");
        boolean deleted = userRepository.existsById(id);
        if (!deleted) {
            throw new AppException(messageSource.getMessage("user.couldntDelete", new Object[0], LocaleContextHolder.getLocale()));
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getByEmail(String email) {
        log.debug("Calling getByEmail {}", email);
        User user = userRepository.findByEmail(email).orElse(null);
        return userMapper.toDto(user);
    }


    private void validation(UserRegistrationDto userRegistrationDto) {
        String emailToBeSaved = userRegistrationDto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved).orElse(null);
        if (byEmail != null) {
            throw new UserInputValidationException(messageSource.getMessage("user.emailAlreadyInUse", new Object[0], LocaleContextHolder.getLocale()));
        }
    }
}
