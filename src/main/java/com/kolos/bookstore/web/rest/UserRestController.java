package com.kolos.bookstore.web.rest;

import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import com.kolos.bookstore.service.exception.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping
    public Page<UserDto> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/search_lastName")
    public Page<UserDto> searchLastName(@RequestParam String lastName, Pageable pageable) {
        return userService.getByLastName(lastName, pageable);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserDto userDto, BindingResult errors) {
        checkErrors(errors);
        userDto.setId(id);
        return userService.update(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Validated UserDto userDto, BindingResult errors) {
        checkErrors(errors);
        UserDto created = userService.create(userDto);
        return buildResponseCreated(created);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody @Valid UserRegistrationDto registrationDto, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        UserDto userDto = userService.registration(registrationDto);
        return buildResponseCreated(userDto);
    }


    private ResponseEntity<UserDto> buildResponseCreated(UserDto created) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(created))
                .body(created);
    }

    private URI getLocation(UserDto userDto) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();
    }

    private void checkErrors(BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }


}
