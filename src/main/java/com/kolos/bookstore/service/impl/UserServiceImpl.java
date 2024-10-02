package com.kolos.bookstore.service.impl;

import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.repository.UserRepository;
import com.kolos.bookstore.service.EncryptionService;
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
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final UserMapper userMapper;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        log.info("UserRegistrationDto: {}", userRegistrationDto);
        validation(userRegistrationDto);
        User user = userMapper.toEntityRegistrationUser(userRegistrationDto);
        user.setRole(User.Role.USER);
        user.setPassword(encryptionService.hash(userRegistrationDto.getPassword()));
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

    @Transactional
    @Override
    public UserDto create(UserDto dto) {
        String emailToBeSaved = dto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved).orElse(null);
        if (byEmail != null) {
            throw new UserInputValidationException(messageSource.getMessage("user.emailExists", new Object[0], LocaleContextHolder.getLocale()));
        }
        User user = userMapper.toEntity(dto);
        user.setPassword(encryptionService.hash(dto.getPassword()));
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
        user.setPassword(encryptionService.hash(dto.getPassword()));
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

    @Transactional
    @Override
    public UserDto login(String email, String password) {
        log.debug("Calling login {}", email);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !encryptionService.verify(password, user.getPassword())) {
            throw new AuthenticationFailedException(messageSource.getMessage("user.notPasAndEmail", new Object[0], LocaleContextHolder.getLocale()));
        }
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
