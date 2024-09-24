package com.kolos.bookstore.web.controller;

import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    public String getUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "5") int pageSize,
            Model model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<UserDto> pages = userService.getAll(pageable);
        addAttribute(model, pages);
        return "user/users";
    }



    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        UserDto userDto = userService.get(id);
        model.addAttribute("user", userDto);
        return "user/user";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users/getAll";
    }


    @GetMapping("/create")
    public String createFormUser() {
        return "user/userRegistrationForm";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@ModelAttribute UserDto userDto, Model model) {
        UserDto createdUser = userService.create(userDto);
        model.addAttribute("user", createdUser);
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
    public String editUserForm(@PathVariable Long id, Model model) {
        UserDto userDto = userService.get(id);
        model.addAttribute("user", userDto);
        return "user/userEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute UserDto userDto) {
        UserDto editedUser = userService.update(userDto);
        return "redirect:/users/" + editedUser.getId();
    }

    @GetMapping("/search_lastName")
    public String searchByLastNameUser(@RequestParam String lastName, Model model,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "page_size", defaultValue = "5") int pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<UserDto> pages = userService.getByLastName(lastName, pageable);
        addAttribute(model, pages);
        return "user/usersSearch";
    }

    private static void addAttribute(Model model, Page<UserDto> pages) {
        model.addAttribute("users", pages.getContent());
        model.addAttribute("page", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
    }


}
