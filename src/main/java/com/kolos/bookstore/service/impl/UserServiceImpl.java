package com.kolos.bookstore.service.impl;

import com.kolos.bookstore.service.util.MassageManager;
import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.repository.UserRepository;
import com.kolos.bookstore.service.DigestService;
import com.kolos.bookstore.service.ServiceMapper;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import com.kolos.bookstore.service.exception.AppException;
import com.kolos.bookstore.service.exception.DuplicateEmailException;
import com.kolos.bookstore.service.exception.NotFoundException;
import com.kolos.bookstore.service.exception.UserInputValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kolos.bookstore.service.util.PageUtil.getTotalPages;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DigestService digestService;
    private final ServiceMapper serviceMapper;


    @Override
    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        log.info("UserRegistrationDto: {}", userRegistrationDto);
        validation(userRegistrationDto);
        User user = serviceMapper.toEntityRegistrationUser(userRegistrationDto);
        User userRegistration = userRepository.save(user);
        log.debug("Calling create {}", userRegistration);
        UserDto userDto = serviceMapper.toDtoRegistrationUser(user);
        return userDto;
    }


    @Override
    public UserDto get(Long id) {
        log.debug("Calling getById {}", id);
        User user = userRepository.find(id);
        if (user == null) {
            throw new NotFoundException(MassageManager.INSTANCE.getMessage("user.notFoundUser") + id);
        }
        return serviceMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll(PageableDto pageableDto) {
        log.debug("Colling getAll");
        List<UserDto> users = userRepository.findAll(pageableDto.getLimit(), pageableDto.getOffset()).stream()
                .map(serviceMapper::toDto)
                .toList();
        int count = userRepository.countAll();
        int pages = getTotalPages(pageableDto, count);
        pageableDto.setTotalItems(count);
        pageableDto.setTotalPages(pages);
        return users;

    }


    @Override
    public UserDto create(UserDto dto) {
        log.debug("Calling create");
        String emailToBeSaved = dto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved);
        if (byEmail != null) {
            throw new DuplicateEmailException(MassageManager.INSTANCE.getMessage("user.emailExists") + emailToBeSaved);
        }
        User user = serviceMapper.toEntity(dto);
        user.setPassword(digestService.hash(dto.getPassword()));
        User created = userRepository.save(user);
        return serviceMapper.toDto(created);
    }

    @Override
    public UserDto update(UserDto dto) {
        String emailToBeSaved = dto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved);
        if (byEmail != null && !byEmail.getId().equals(dto.getId())) {
            throw new RuntimeException(MassageManager.INSTANCE.getMessage("user.emailExists") + emailToBeSaved);
        }
        log.debug("Calling update");
        User user = serviceMapper.toEntity(dto);
        user.setPassword(digestService.hash(dto.getPassword()));
        User updated = userRepository.update(user);
        return serviceMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling delete");
        boolean deleted = userRepository.delete(id);
        if (!deleted) {
            throw new AppException(MassageManager.INSTANCE.getMessage("user.couldntDelete") + id);
        }
    }

    @Override
    public UserDto getByEmail(String email) {
        log.debug("Calling getByEmail {}", email);
        User user = userRepository.findByEmail(email);
        return serviceMapper.toDto(user);
    }

    @Override
    public List<UserDto> getByLastName(String lastName, PageableDto pageableDto) {
        log.debug("Calling getByLastName {}", lastName);
        List<UserDto> users = userRepository.findByLastName(lastName, pageableDto.getLimit(), pageableDto.getOffset())
                .stream()
                .map(serviceMapper::toDto)
                .toList();
        int count = userRepository.countAll();
        int pages = getTotalPages(pageableDto, count);
        pageableDto.setTotalItems(count);
        pageableDto.setTotalPages(pages);
        return users;
    }

    @Override
    public UserDto login(String email, String password) {
        log.debug("Calling login {}", email);
        String hashed = digestService.hash(password);
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(hashed)) {
            throw new UserInputValidationException(MassageManager.INSTANCE.getMessage("user.notPasAndEmail"));
        }
        return serviceMapper.toDto(user);
    }


    private void validation(UserRegistrationDto userRegistrationDto) {
        String emailToBeSaved = userRegistrationDto.getEmail();
        User byEmail = userRepository.findByEmail(emailToBeSaved);
        List<String> errors = new ArrayList<>();
        if (byEmail != null) {
            errors.add(MassageManager.INSTANCE.getMessage("user.emailAlreadyInUse"));
        } else if (userRegistrationDto.getPassword().length() < 6) {
            errors.add(MassageManager.INSTANCE.getMessage("user.passwordTooShort"));
        }
        if (!errors.isEmpty()) {
            throw new UserInputValidationException(errors);
        }
    }
}
