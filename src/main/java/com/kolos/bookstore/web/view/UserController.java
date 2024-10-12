package com.kolos.bookstore.web.view;

import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public String getUsers(Model model, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC,"id"));
        Page<UserDto> pages = userService.getAll(pageable);
        addAttribute(model, pageable, pages);
        return "user/users";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    public String getUser(@PathVariable Long id, Model model) {
        UserDto userDto = userService.get(id);
        model.addAttribute("user", userDto);
        return "user/user";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users/getAll";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createFormUser() {
        return "user/userRegistrationForm";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createUser(@ModelAttribute @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/userRegistrationForm";
        }
        UserDto createdUser = userService.create(userDto);
        model.addAttribute("userDto", createdUser);
        return "redirect:users/" + createdUser.getId();
    }

    @GetMapping("/registration")
    public String registrationFormUser() {
        return "user/userRegistrationForm";
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute UserRegistrationDto userRegistrationDto, Model model) {
        UserDto userDto = userService.registration(userRegistrationDto);
        model.addAttribute("user", userDto);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserDto userDto = userService.get(id);
        model.addAttribute("user", userDto);
        return "user/userEditForm";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String editUser(@ModelAttribute UserDto userDto) {
        UserDto editedUser = userService.update(userDto);
        return "redirect:/users/" + editedUser.getId();
    }

    @GetMapping("/search_lastName")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public String searchByLastNameUser(@RequestParam String lastName, Model model, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        Page<UserDto> pages = userService.getByLastName(lastName, pageable);
        addAttribute(model, pageable, pages);
        return "user/usersSearch";
    }

    @ModelAttribute
    public UserDto newUser() {
        return new UserDto();
    }

    private static void addAttribute(Model model, Pageable pageable, Page<UserDto> pages) {
        model.addAttribute("users", pages.getContent());
        model.addAttribute("page", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("size", pageable.getPageSize());
    }
}
