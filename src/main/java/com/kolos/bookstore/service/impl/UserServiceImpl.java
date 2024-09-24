package com.kolos.bookstore.service.impl;

import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.repository.UserRepository;
import com.kolos.bookstore.service.DigestService;
import com.kolos.bookstore.service.ServiceMapper;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import com.kolos.bookstore.service.exception.*;
import com.kolos.bookstore.service.util.MessageManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DigestService digestService;
    private final ServiceMapper serviceMapper;
    private final MessageManager messageManager;

    @Transactional
    @Override
    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        log.info("UserRegistrationDto: {}", userRegistrationDto);
        validation(userRegistrationDto);
        User user = serviceMapper.toEntityRegistrationUser(userRegistrationDto);
        user.setRole(User.Role.USER);
        User userRegistration = userRepository.save(user);
        log.debug("Calling create {}", userRegistration);
        return serviceMapper.toDtoRegistrationUser(user);
    }


    @Override
    public UserDto get(Long id) {
        log.debug("Calling getById {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException(messageManager.getMessage("user.notFoundUser") + id);
        }
        return serviceMapper.toDto(user);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        log.debug("Colling getAll");
        return userRepository.findAll(pageable).map(serviceMapper::toDto);
    }

    @Override
    public Page<UserDto> getByLastName(String lastName, Pageable pageable) {
        log.debug("Calling getByLastName {}", lastName);
        return userRepository.findAllByLastName(lastName, pageable).map(serviceMapper::toDto);
    }

    @Transactional
    @Override
    public UserDto create(UserDto dto) {
        log.debug("Calling create");
        String emailToBeSaved = dto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved).orElse(null);
        if (byEmail != null) {
            throw new UserInputValidationException(messageManager.getMessage("user.emailExists") + emailToBeSaved);
        }
        User user = serviceMapper.toEntity(dto);
        user.setPassword(digestService.hash(dto.getPassword()));
        User created = userRepository.save(user);
        return serviceMapper.toDto(created);
    }

    @Transactional
    @Override
    public UserDto update(UserDto dto) {
        String emailToBeSaved = dto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved).orElse(null);
        if (byEmail != null && !byEmail.getId().equals(dto.getId())) {
            throw new UpdateFailedException(messageManager.getMessage("user.emailExists") + emailToBeSaved);
        }
        log.debug("Calling update");
        User user = serviceMapper.toEntity(dto);
        user.setPassword(digestService.hash(dto.getPassword()));
        User updated = userRepository.save(user);
        return serviceMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.debug("Calling delete");
        boolean deleted = userRepository.existsById(id);
        if (!deleted) {
            throw new AppException(messageManager.getMessage("user.couldntDelete") + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getByEmail(String email) {
        log.debug("Calling getByEmail {}", email);
        User user = userRepository.findByEmail(email).orElse(null);
        return serviceMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDto login(String email, String password) {
        log.debug("Calling login {}", email);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !digestService.verify(password, user.getPassword())) {
            throw new AuthenticationFailedException(messageManager.getMessage("user.notPasAndEmail"));
        }
        return serviceMapper.toDto(user);
    }


    private void validation(UserRegistrationDto userRegistrationDto) {
        String emailToBeSaved = userRegistrationDto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved).orElse(null);
        if (byEmail != null) {
            throw new UserInputValidationException(messageManager.getMessage("user.emailAlreadyInUse"));
        }
    }
}
